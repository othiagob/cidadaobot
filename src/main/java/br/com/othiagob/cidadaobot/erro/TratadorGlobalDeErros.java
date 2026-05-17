package br.com.othiagob.cidadaobot.erro;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> tratarMethodArgumentNotValidException(
      MethodArgumentNotValidException exception) {

    Map<String, String> erros = new HashMap<>();

    exception
        .getBindingResult()
        .getAllErrors()
        .forEach(
            erro -> {
              String campo = ((FieldError) erro).getField();
              String mensagem = erro.getDefaultMessage();

              erros.put(campo, mensagem);
            });

    return ResponseEntity.badRequest().body(erros);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErroRespostaDTO> tratarConstraintViolationException(
      ConstraintViolationException exception, HttpServletRequest request) {

    String mensagem =
        exception.getConstraintViolations().stream()
            .findFirst()
            .map(violacao -> violacao.getMessage())
            .orElse("Parâmetro inválido.");

    ErroRespostaDTO resposta =
        new ErroRespostaDTO(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Requisição inválida",
            mensagem,
            request.getRequestURI());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErroRespostaDTO> tratarMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException exception, HttpServletRequest request) {

    String mensagem = "Parâmetro inválido.";

    if ("data".equals(exception.getName())) {
      mensagem = "Formato de data inválido. Use o padrão yyyy-MM-dd.";
    }

    ErroRespostaDTO resposta =
        new ErroRespostaDTO(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Requisição inválida",
            mensagem,
            request.getRequestURI());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ErroRespostaDTO> tratarMissingServletRequestParameterException(
      MissingServletRequestParameterException exception, HttpServletRequest request) {

    String mensagem =
        "O parâmetro obrigatório '" + exception.getParameterName() + "' não foi informado.";

    ErroRespostaDTO resposta =
        new ErroRespostaDTO(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Requisição inválida",
            mensagem,
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
            exception.getMessage(),
            request.getRequestURI());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resposta);
  }
}
