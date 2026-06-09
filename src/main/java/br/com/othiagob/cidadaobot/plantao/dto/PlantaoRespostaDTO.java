package br.com.othiagob.cidadaobot.plantao.dto;

import br.com.othiagob.cidadaobot.plantao.EscalaPlantao;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Schema(description = "Dados individuais de uma farmácia em plantão.")
public record PlantaoRespostaDTO(
    @Schema(description = "Data oficial da escala de plantão.", example = "2026-05-19")
        java.time.LocalDate dataPlantao,
    @Schema(description = "Horário de início do plantão.", example = "07:00") LocalTime iniciaAs,
    @Schema(description = "Horário de término do plantão.", example = "07:00") LocalTime terminaAs,
    @Schema(description = "Dados da farmácia responsável pelo plantão.")
        FarmaciaRespostaDTO farmacia,
    @Schema(
            description = "Mensagem textual pronta para exibição ao usuário.",
            example = "FARMACIA REAL está de plantão das 07:00 às 07:00.")
        String mensagem)
    implements Serializable {

  private static final DateTimeFormatter FORMATADOR_HORA = DateTimeFormatter.ofPattern("HH:mm");

  public static PlantaoRespostaDTO from(EscalaPlantao escala) {
    String nomeFarmacia = escala.getFarmacia().getNome();
    String horarioInicio = escala.getIniciaAs().format(FORMATADOR_HORA);
    String horarioTermino = escala.getTerminaAs().format(FORMATADOR_HORA);

    String mensagem =
        String.format(
            "%s está de plantão das %s às %s.", nomeFarmacia, horarioInicio, horarioTermino);

    return new PlantaoRespostaDTO(
        escala.getDataPlantao(),
        escala.getIniciaAs(),
        escala.getTerminaAs(),
        FarmaciaRespostaDTO.from(escala.getFarmacia()),
        mensagem);
  }
}
