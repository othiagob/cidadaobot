package br.com.othiagob.cidadaobot.dto;

import java.time.LocalDateTime;

public record RespostaApiDTO<T>(
    boolean sucesso, String mensagem, T dados, LocalDateTime timestamp) {
  public static <T> RespostaApiDTO<T> sucesso(String mensagem, T dados) {
    return new RespostaApiDTO<>(true, mensagem, dados, LocalDateTime.now());
  }

  public static <T> RespostaApiDTO<T> erro(String mensagem, T dados) {
    return new RespostaApiDTO<T>(false, mensagem, dados, LocalDateTime.now());
  }
}
