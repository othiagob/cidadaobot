package br.com.othiagob.cidadaobot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(name = "RespostaApi", description = "Envelope padrão de resposta da API.")
public record RespostaApiDTO<T>(
    @Schema(
            description = "Indica se a requisição foi processada com sucesso.",
            example = "true")
        boolean sucesso,
    @Schema(description = "Mensagem resumida da resposta.", example = "Consulta realizada com sucesso.")
        String mensagem,
    @Schema(description = "Conteúdo principal da resposta.") T dados,
    @Schema(
            description = "Data e hora em que a resposta foi gerada.",
            example = "2026-05-19T21:30:00")
        LocalDateTime timestamp) {
  public static <T> RespostaApiDTO<T> sucesso(String mensagem, T dados) {
    return new RespostaApiDTO<>(true, mensagem, dados, LocalDateTime.now());
  }

  public static <T> RespostaApiDTO<T> erro(String mensagem, T dados) {
    return new RespostaApiDTO<T>(false, mensagem, dados, LocalDateTime.now());
  }
}
