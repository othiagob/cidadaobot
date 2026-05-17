package br.com.othiagob.cidadaobot.plantao;

import br.com.othiagob.cidadaobot.dto.RespostaApiDTO;
import br.com.othiagob.cidadaobot.plantao.dto.ConsultaPlantaoAtualRespostaDTO;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/plantoes")
@Validated
public class PlantaoController {

  private final PlantaoService plantaoService;

  public PlantaoController(PlantaoService plantaoService) {
    this.plantaoService = plantaoService;
  }

  @GetMapping
  public ResponseEntity<RespostaApiDTO<ConsultaPlantaoAtualRespostaDTO>> buscarPlantaoPorData(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
      @RequestParam(required = false)
          @Pattern(regexp = ".*\\S.*", message = "O distrito não pode ser vazio.")
          String distrito) {

    ConsultaPlantaoAtualRespostaDTO resposta =
        plantaoService.consultarPlantaoPorData(data, distrito);

    return ResponseEntity.ok(RespostaApiDTO.sucesso(resposta.mensagem(), resposta));
  }

  @GetMapping("/atual")
  public ResponseEntity<RespostaApiDTO<ConsultaPlantaoAtualRespostaDTO>> buscarPlantaoAtual(
      @RequestParam(required = false)
          @Pattern(regexp = ".*\\S.*", message = "O distrito não pode ser vazio.")
          String distrito) {

    ConsultaPlantaoAtualRespostaDTO resposta = plantaoService.consultarPlantaoAtual(distrito);

    return ResponseEntity.ok(RespostaApiDTO.sucesso(resposta.mensagem(), resposta));
  }
}
