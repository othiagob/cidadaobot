package br.com.othiagob.cidadaobot.plantao;

import br.com.othiagob.cidadaobot.plantao.dto.ConsultaPlantaoAtualRespostaDTO;
import br.com.othiagob.cidadaobot.plantao.dto.PlantaoRespostaDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class PlantaoService {
  private static final Logger logger = LoggerFactory.getLogger(PlantaoService.class);

  private static final LocalTime HORARIO_INICIO_PLANTAO = LocalTime.of(7, 0);

  private final EscalaPlantaoRepository escalaPlantaoRepository;

  public PlantaoService(EscalaPlantaoRepository escalaPlantaoRepository) {
    this.escalaPlantaoRepository = escalaPlantaoRepository;
  }

  public Optional<LocalDate> determinarDataPlantaoAtivo(LocalDateTime momento) {
    if (momento == null) {
      throw new IllegalArgumentException("O momento da consulta não pode ser nulo.");
    }

    LocalDate dataAtual = momento.toLocalDate();
    LocalTime horarioAtual = momento.toLocalTime();

    if (horarioAtual.isBefore(HORARIO_INICIO_PLANTAO)) {
      return Optional.of(dataAtual.minusDays(1));
    }

    return Optional.of(dataAtual);
  }

  public List<EscalaPlantao> buscarPlantoesAtivos(LocalDateTime momento) {
    LocalDate dataPlantaoAtivo = determinarDataPlantaoAtivo(momento).orElseThrow();

    return escalaPlantaoRepository.findByDataPlantao(dataPlantaoAtivo);
  }

  public List<EscalaPlantao> buscarPlantoesAtivosPorDistrito(
      LocalDateTime momento, String distrito) {

    if (distrito == null || distrito.isBlank()) {
      throw new IllegalArgumentException("O distrito não pode ser nulo ou vazio.");
    }

    LocalDate dataPlantaoAtivo = determinarDataPlantaoAtivo(momento).orElseThrow();

    return escalaPlantaoRepository.findByDataPlantaoAndFarmaciaDistritoIgnoreCase(
        dataPlantaoAtivo, distrito.trim());
  }

  @Cacheable(
      value = "plantoesPorData",
      key =
          "'data:' + #data + ':distrito:' + "
              + "(T(org.springframework.util.StringUtils).hasText(#distrito) "
              + "? #distrito.trim().toLowerCase() : 'todos')")
  public ConsultaPlantaoAtualRespostaDTO consultarPlantaoPorData(LocalDate data, String distrito) {
    logger.debug(
        "Cache miss: consultando plantão por data no PostgreSQL. data={}, distrito={}",
        data,
        distrito);

    if (data == null) {
      throw new IllegalArgumentException("A data da consulta não pode ser nula.");
    }

    List<EscalaPlantao> escalas = buscarEscalasPorData(data, distrito);

    if (escalas.isEmpty()) {
      return new ConsultaPlantaoAtualRespostaDTO(
          data,
          true,
          "Não encontrei escala de plantão cadastrada para esta data no sistema.",
          List.of());
    }

    List<PlantaoRespostaDTO> plantoes = escalas.stream().map(PlantaoRespostaDTO::from).toList();

    return new ConsultaPlantaoAtualRespostaDTO(
        data, true, "Plantão encontrado para a data informada.", plantoes);
  }

  public ConsultaPlantaoAtualRespostaDTO consultarPlantaoAtual(String distrito) {
    return consultarPlantaoAtual(distrito, LocalDateTime.now());
  }

  public ConsultaPlantaoAtualRespostaDTO consultarPlantaoAtual(
      String distrito, LocalDateTime momento) {

    LocalDate dataReferencia = determinarDataPlantaoAtivo(momento).orElseThrow();

    List<EscalaPlantao> escalas = buscarEscalasDoPlantao(momento, distrito);

    if (escalas.isEmpty()) {
      return new ConsultaPlantaoAtualRespostaDTO(
          dataReferencia,
          true,
          "Não encontrei escala de plantão cadastrada para esta data no sistema.",
          List.of());
    }

    List<PlantaoRespostaDTO> plantoes = escalas.stream().map(PlantaoRespostaDTO::from).toList();

    return new ConsultaPlantaoAtualRespostaDTO(
        dataReferencia, true, "Plantão ativo encontrado.", plantoes);
  }

  private List<EscalaPlantao> buscarEscalasPorData(LocalDate data, String distrito) {
    if (distrito == null || distrito.isBlank()) {
      return escalaPlantaoRepository.findByDataPlantao(data);
    }

    return escalaPlantaoRepository.findByDataPlantaoAndFarmaciaDistritoIgnoreCase(
        data, distrito.trim());
  }

  private List<EscalaPlantao> buscarEscalasDoPlantao(LocalDateTime momento, String distrito) {

    if (distrito == null || distrito.isBlank()) {
      return buscarPlantoesAtivos(momento);
    }

    return buscarPlantoesAtivosPorDistrito(momento, distrito);
  }
}
