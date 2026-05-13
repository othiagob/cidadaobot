package br.com.othiagob.cidadaobot.plantao.dto;

import br.com.othiagob.cidadaobot.plantao.EscalaPlantao;
import java.time.LocalDate;
import java.time.LocalTime;

public record PlantaoRespostaDTO(
    LocalDate dataPlantao,
    LocalTime iniciaAs,
    LocalTime terminaAs,
    FarmaciaRespostaDTO farmacia,
    String mensagem) {
  public static PlantaoRespostaDTO from(EscalaPlantao escala) {
    String mensagem =
        escala.getFarmacia().getNome()
            + " está de plantão das "
            + escala.getIniciaAs()
            + " às "
            + escala.getTerminaAs()
            + ".";

    return new PlantaoRespostaDTO(
        escala.getDataPlantao(),
        escala.getIniciaAs(),
        escala.getTerminaAs(),
        FarmaciaRespostaDTO.from(escala.getFarmacia()),
        mensagem);
  }
}
