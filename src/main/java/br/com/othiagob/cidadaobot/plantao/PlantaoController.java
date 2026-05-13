package br.com.othiagob.cidadaobot.plantao;

import br.com.othiagob.cidadaobot.plantao.dto.ConsultaPlantaoAtualRespostaDTO;
import br.com.othiagob.cidadaobot.plantao.dto.PlantaoRespostaDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
  public ConsultaPlantaoAtualRespostaDTO buscarPlantaoAtual(
      @RequestParam(required = false) String distrito) {
    LocalDateTime agora = LocalDateTime.now();
    Optional<LocalDate> dataReferencia = plantaoService.determinarDataPlantaoAtivo(agora);

    if (dataReferencia.isEmpty()) {
      return new ConsultaPlantaoAtualRespostaDTO(
          null,
          false,
          "Não há plantão ativo neste horário. O plantão funciona das 19:00 às 07:00.",
          List.of());
    }

    List<EscalaPlantao> escalas = buscarEscalas(agora, distrito);

    if (escalas.isEmpty()) {
      return new ConsultaPlantaoAtualRespostaDTO(
          dataReferencia.get(),
          true,
          "Não encontrei escala de plantão cadastrada para esta data no sistema.",
          List.of());
    }

    List<PlantaoRespostaDTO> plantoes = escalas.stream().map(PlantaoRespostaDTO::from).toList();

    return new ConsultaPlantaoAtualRespostaDTO(
        dataReferencia.get(), true, "Plantão ativo encontrado.", plantoes);
  }

  private List<EscalaPlantao> buscarEscalas(LocalDateTime agora, String distrito) {
    if (distrito == null || distrito.isBlank()) {
      return plantaoService.buscarPlantoesAtivos(agora);
    }

    return plantaoService.buscarPlantoesAtivosPorDistrito(agora, distrito);
  }
}
