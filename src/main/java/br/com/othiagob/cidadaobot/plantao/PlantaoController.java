package br.com.othiagob.cidadaobot.plantao;

import br.com.othiagob.cidadaobot.dto.RespostaApiDTO;
import br.com.othiagob.cidadaobot.plantao.dto.ConsultaPlantaoAtualRespostaDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/plantoes")
public class PlantaoController {

  private final PlantaoService plantaoService;

  public PlantaoController(PlantaoService plantaoService) {
    this.plantaoService = plantaoService;
  }

  @GetMapping("/atual")
  public ResponseEntity<RespostaApiDTO<ConsultaPlantaoAtualRespostaDTO>> buscarPlantaoAtual(
      @RequestParam(required = false) String distrito) {

    ConsultaPlantaoAtualRespostaDTO resposta = plantaoService.consultarPlantaoAtual(distrito);

    return ResponseEntity.ok(RespostaApiDTO.sucesso(resposta.mensagem(), resposta));
  }
}
