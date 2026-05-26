package br.com.othiagob.cidadaobot.erro;

import br.com.othiagob.cidadaobot.dto.RespostaApiDTO;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class TratadorGlobalDeErros {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<RespostaApiDTO> tratarErroDeValidacao(MethodArgumentNotValidException ex) {
    String mensagem =
        ex.getBindingResult().getFieldErrors().stream()
            .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
            .collect(Collectors.joining("; "));

    RespostaApiDTO resposta = new RespostaApiDTO(false, mensagem, null, LocalDateTime.now());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<RespostaApiDTO> tratarViolacaoDeRestricao(ConstraintViolationException ex) {
    String mensagem =
        ex.getConstraintViolations().stream()
            .map(violacao -> violacao.getMessage())
            .collect(Collectors.joining("; "));

    RespostaApiDTO resposta = new RespostaApiDTO(false, mensagem, null, LocalDateTime.now());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<RespostaApiDTO> tratarParametroObrigatorioAusente(
      MissingServletRequestParameterException ex) {
    String mensagem = "Parâmetro obrigatório ausente: " + ex.getParameterName() + ".";

    RespostaApiDTO resposta = new RespostaApiDTO(false, mensagem, null, LocalDateTime.now());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<RespostaApiDTO> tratarTipoDeParametroInvalido(
      MethodArgumentTypeMismatchException ex) {
    String mensagem = "Parâmetro inválido: " + ex.getName() + ".";

    if ("data".equals(ex.getName())) {
      mensagem = "Data inválida. Formato esperado: AAAA-MM-DD.";
    }

    RespostaApiDTO resposta = new RespostaApiDTO(false, mensagem, null, LocalDateTime.now());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<RespostaApiDTO> tratarArgumentoInvalido(IllegalArgumentException ex) {
    RespostaApiDTO resposta = new RespostaApiDTO(false, ex.getMessage(), null, LocalDateTime.now());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<RespostaApiDTO> tratarErroInterno(Exception ex) {
    RespostaApiDTO resposta =
        new RespostaApiDTO(false, "Erro interno inesperado na API.", null, LocalDateTime.now());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resposta);
  }
}
