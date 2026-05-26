package br.com.othiagob.cidadaobot.conversa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record ConversaRegistroRequestDTO(
    @NotBlank(message = "Telefone do usuário é obrigatório.")
        @Size(max = 20, message = "Telefone do usuário deve ter no máximo 20 caracteres.")
        String telefoneUsuario,
    @NotBlank(message = "Mensagem do usuário é obrigatória.") String mensagemUsuario,
    @NotBlank(message = "Resposta enviada é obrigatória.") String respostaEnviada,
    @Size(max = 100, message = "Intenção detectada deve ter no máximo 100 caracteres.")
        String intencaoDetectada,
    @Size(max = 100, message = "Distrito detectado deve ter no máximo 100 caracteres.")
        String distritoDetectado,
    LocalDate dataReferencia,
    @NotBlank(message = "Origem é obrigatório.")
        @Size(max = 50, message = "Origem deve ter no máximo 50 caracteres.")
        String origem) {}
