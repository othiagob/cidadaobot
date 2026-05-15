package br.com.othiagob.cidadaobot.erro;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorGlobalDeErros {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErroRespostaDTO> tratarIllegalArgumentException(
      IllegalArgumentException exception, HttpServletRequest request) {

    ErroRespostaDTO resposta =
        new ErroRespostaDTO(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Requisição inválida",
            exception.getMessage(),
            request.getRequestURI());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErroRespostaDTO> tratarRuntimeException(
      RuntimeException exception, HttpServletRequest request) {

    ErroRespostaDTO resposta =
        new ErroRespostaDTO(
            LocalDateTime.now(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Erro interno",
            "Ocorreu um erro inesperado na aplicação.",
            request.getRequestURI());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resposta);
  }
}
