package br.com.othiagob.cidadaobot.conversa.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ConversaRespostaDTO(
    Long id,
    String telefoneUsuario,
    String mensagemUsuario,
    String respostaEnviada,
    String intencaoDetectada,
    String distritoDetectado,
    LocalDate dataReferencia,
    String origem,
    LocalDateTime criadaEm) {}
