package br.com.othiagob.cidadaobot.erro;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(name = "ErroResposta", description = "Resposta padronizada para erros tratados pela API.")
public record ErroRespostaDTO(
    @Schema(
            description = "Data e hora em que o erro foi registrado.",
            example = "2026-05-19T21:30:00")
        LocalDateTime timestamp,
    @Schema(description = "Código HTTP do erro.", example = "400") int status,
    @Schema(description = "Descrição curta do erro HTTP.", example = "Bad Request") String erro,
    @Schema(description = "Mensagem explicando o motivo do erro.", example = "O distrito não pode ser vazio.")
        String mensagem,
    @Schema(description = "Caminho da requisição que gerou o erro.", example = "/api/plantoes")
        String caminho) {}
