package br.com.othiagob.cidadaobot.conversa;

import br.com.othiagob.cidadaobot.conversa.dto.ConversaRegistroRequestDTO;
import br.com.othiagob.cidadaobot.conversa.dto.ConversaRespostaDTO;
import br.com.othiagob.cidadaobot.dto.RespostaApiDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(
    name = "Conversas",
    description = "Endpoints para registrar e consultar histórico de interações do CidadãoBot.")
@RestController
@RequestMapping("/api/conversas")
public class ConversaController {

  private final ConversaService conversaService;

  public ConversaController(ConversaService conversaService) {
    this.conversaService = conversaService;
  }

  @Operation(
      summary = "Registrar interação",
      description = "Registra uma interação realizada pelo usuário com o CidadãoBot.")
  @PostMapping
  public ResponseEntity<RespostaApiDTO> registrarInteracao(
      @Valid @RequestBody ConversaRegistroRequestDTO requestDTO) {
    ConversaRespostaDTO conversaRegistrada = conversaService.registrarInteracao(requestDTO);

    RespostaApiDTO resposta =
        new RespostaApiDTO(
            true, "Interação registrada com sucesso.", conversaRegistrada, LocalDateTime.now());

    return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
  }

  @Operation(
      summary = "Consultar histórico por telefone",
      description = "Consulta o histórico de interações de um usuário pelo telefone.")
  @GetMapping
  public ResponseEntity<RespostaApiDTO> consultarHistoricoPorTelefone(
      @RequestParam String telefone, @RequestParam(required = false) Integer limite) {
    if (!StringUtils.hasText(telefone)) {
      throw new IllegalArgumentException("Telefone do usuário é obrigatório.");
    }

    List<ConversaRespostaDTO> historico =
        conversaService.consultarHistoricoPorTelefone(telefone, limite);

    RespostaApiDTO resposta =
        new RespostaApiDTO(
            true, "Histórico de conversas consultado com sucesso.", historico, LocalDateTime.now());

    return ResponseEntity.ok(resposta);
  }
}
