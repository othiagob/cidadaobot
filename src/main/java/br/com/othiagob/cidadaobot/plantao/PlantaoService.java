package br.com.othiagob.cidadaobot.plantao;

import br.com.othiagob.cidadaobot.plantao.dto.ConsultaPlantaoAtualRespostaDTO;
import br.com.othiagob.cidadaobot.plantao.dto.PlantaoRespostaDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PlantaoService {
  private static final LocalTime HORARIO_INICIO_PLANTAO = LocalTime.of(19, 0);
  private static final LocalTime HORARIO_FINAL_PLANTAO = LocalTime.of(7, 0);

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

    if (!horarioAtual.isBefore(HORARIO_INICIO_PLANTAO)) {
      return Optional.of(dataAtual);
    }

    if (horarioAtual.isBefore(HORARIO_FINAL_PLANTAO)) {
      return Optional.of(dataAtual.minusDays(1));
    }
    return Optional.empty();
  }

  public List<EscalaPlantao> buscarPlantoesAtivos(LocalDateTime momento) {
    Optional<LocalDate> dataPlantaoAtivo = determinarDataPlantaoAtivo(momento);

    if (dataPlantaoAtivo.isEmpty()) {
      return List.of();
    }

    return escalaPlantaoRepository.findByDataPlantao(dataPlantaoAtivo.get());
  }

  public List<EscalaPlantao> buscarPlantoesAtivosPorDistrito(
      LocalDateTime momento, String distrito) {
    if (distrito == null || distrito.isBlank()) {
      throw new IllegalArgumentException("O distrito não pode ser nulo ou vazio.");
    }

    Optional<LocalDate> dataPlantaoAtivo = determinarDataPlantaoAtivo(momento);

    if (dataPlantaoAtivo.isEmpty()) {
      return List.of();
    }

    return escalaPlantaoRepository.findByDataPlantaoAndFarmaciaDistritoIgnoreCase(
        dataPlantaoAtivo.get(), distrito.trim());
  }

  public ConsultaPlantaoAtualRespostaDTO consultarPlantaoAtual(String distrito) {
    LocalDateTime agora = LocalDateTime.now();
    Optional<LocalDate> dataReferencia = determinarDataPlantaoAtivo(agora);

    if (dataReferencia.isEmpty()) {
      return new ConsultaPlantaoAtualRespostaDTO(
          null,
          false,
          "Não há plantão ativo neste horário. O plantão funciona das 19:00 às 07:00.",
          List.of());
    }
    List<EscalaPlantao> escalas =
        distrito == null || distrito.isBlank()
            ? buscarPlantoesAtivos(agora)
            : buscarPlantoesAtivosPorDistrito(agora, distrito);

    if (escalas.isEmpty()) {
      return new ConsultaPlantaoAtualRespostaDTO(
          dataReferencia.get(),
          true,
          "Não encontrei escala de plantão cadastrada para esta data no sistema.",
          List.of());
    }

    List<PlantaoRespostaDTO> plantoes = escalas.stream().map(PlantaoRespostaDTO::from).toList();

    return new ConsultaPlantaoAtualRespostaDTO(
        dataReferencia.get(), true, "Plantão ativo encontrado", plantoes);
  }
}
