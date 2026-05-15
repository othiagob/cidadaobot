package br.com.othiagob.cidadaobot.erro;

import java.time.LocalDateTime;

public record ErroRespostaDTO(
    LocalDateTime timestamp, int status, String erro, String mensagem, String caminho) {}
