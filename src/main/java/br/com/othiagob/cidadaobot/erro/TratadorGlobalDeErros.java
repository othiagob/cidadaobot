package br.com.othiagob.cidadaobot.erro;

import br.com.othiagob.cidadaobot.dto.RespostaApiDTO;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class TratadorGlobalDeErros {

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<RespostaApiDTO> tratarParametroComTipoInvalido(
      MethodArgumentTypeMismatchException excecao) {
    String mensagem = "Parâmetro inválido.";

    if ("data".equals(excecao.getName())) {
      mensagem = "Data inválida. Formato esperado: AAAA-MM-DD.";
    }

    return ResponseEntity.badRequest().body(criarRespostaErro(mensagem));
  }

  @ExceptionHandler(DateTimeParseException.class)
  public ResponseEntity<RespostaApiDTO> tratarDataInvalida(DateTimeParseException excecao) {
    return ResponseEntity.badRequest()
        .body(criarRespostaErro("Data inválida. Formato esperado: AAAA-MM-DD."));
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<RespostaApiDTO> tratarParametroObrigatorioAusente(
      MissingServletRequestParameterException excecao) {
    String mensagem = "Parâmetro obrigatório ausente.";

    if ("data".equals(excecao.getParameterName())) {
      mensagem = "Parâmetro obrigatório ausente: data.";
    }

    return ResponseEntity.badRequest().body(criarRespostaErro(mensagem));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<RespostaApiDTO> tratarErroDeValidacao(
      MethodArgumentNotValidException excecao) {
    String mensagem =
        excecao.getBindingResult().getFieldErrors().stream()
            .map(this::formatarErroDeCampo)
            .collect(Collectors.joining("; "));

    if (mensagem.isBlank()) {
      mensagem = "Erro de validação nos dados enviados.";
    }

    return ResponseEntity.badRequest().body(criarRespostaErro(mensagem));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<RespostaApiDTO> tratarViolacaoDeRestricao(
      ConstraintViolationException excecao) {
    String mensagem =
        excecao.getConstraintViolations().stream()
            .map(violacao -> violacao.getPropertyPath() + ": " + violacao.getMessage())
            .collect(Collectors.joining("; "));

    if (mensagem.isBlank()) {
      mensagem = "Erro de validação nos parâmetros enviados.";
    }

    return ResponseEntity.badRequest().body(criarRespostaErro(mensagem));
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<RespostaApiDTO> tratarCorpoDaRequisicaoInvalido(
      HttpMessageNotReadableException excecao) {
    return ResponseEntity.badRequest()
        .body(criarRespostaErro("Corpo da requisição inválido ou mal formatado."));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<RespostaApiDTO> tratarArgumentoInvalido(IllegalArgumentException excecao) {
    String mensagem = excecao.getMessage();

    if (mensagem == null || mensagem.isBlank()) {
      mensagem = "Argumento inválido.";
    }

    return ResponseEntity.badRequest().body(criarRespostaErro(mensagem));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<RespostaApiDTO> tratarErroInterno(Exception excecao) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(criarRespostaErro("Erro interno inesperado na API."));
  }

  private RespostaApiDTO criarRespostaErro(String mensagem) {
    return new RespostaApiDTO(false, mensagem, null, LocalDateTime.now());
  }

  private String formatarErroDeCampo(FieldError erro) {
    return erro.getField() + ": " + erro.getDefaultMessage();
  }
}
