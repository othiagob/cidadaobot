package br.com.othiagob.cidadaobot.plantao.dto;

import br.com.othiagob.cidadaobot.plantao.EscalaPlantao;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalTime;

@Schema(
    name = "PlantaoResposta",
    description = "Informações de uma escala de plantão retornada pela API.")
public record PlantaoRespostaDTO(
    @Schema(description = "Data oficial da escala de plantão.", example = "2026-05-19")
        LocalDate dataPlantao,
    @Schema(description = "Horário de início do plantão.", example = "19:00") LocalTime iniciaAs,
    @Schema(description = "Horário de término do plantão.", example = "07:00") LocalTime terminaAs,
    @Schema(description = "Dados da farmácia responsável pelo plantão.") FarmaciaRespostaDTO farmacia,
    @Schema(
            description = "Mensagem textual pronta para exibição ao usuário.",
            example = "Farmácia Real está de plantão das 19:00 às 07:00.")
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
