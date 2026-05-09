package br.com.othiagob.cidadaobot.plantao;

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
}
