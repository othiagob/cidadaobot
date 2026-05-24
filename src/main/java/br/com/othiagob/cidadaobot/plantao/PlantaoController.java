package br.com.othiagob.cidadaobot.plantao;

import br.com.othiagob.cidadaobot.dto.RespostaApiDTO;
import br.com.othiagob.cidadaobot.plantao.dto.ConsultaPlantaoAtualRespostaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
    name = "Plantões",
    description = "Endpoints para consulta de farmácias de plantão em Ji-Paraná/RO.")
public class PlantaoController {

  private final PlantaoService plantaoService;

  public PlantaoController(PlantaoService plantaoService) {
    this.plantaoService = plantaoService;
  }

  @Operation(
      summary = "Consultar escala de plantão por data",
      description = "Consulta a escala de plantão para a data informada.")
  @ApiResponse(
      responseCode = "200",
      description = "Consulta realizada com sucesso.",
      content = @Content(schema = @Schema(implementation = RespostaApiDTO.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Parâmetro inválido na requisição.",
      content = @Content(schema = @Schema(implementation = RespostaApiDTO.class)))
  @ApiResponse(
      responseCode = "500",
      description = "Erro interno inesperado.",
      content = @Content(schema = @Schema(implementation = RespostaApiDTO.class)))
  @GetMapping
  public ResponseEntity<RespostaApiDTO<ConsultaPlantaoAtualRespostaDTO>> buscarPlantaoPorData(
      @Parameter(
              description = "Data obrigatória para consulta no formato yyyy-MM-dd.",
              required = true,
              example = "2026-05-19")
          @RequestParam
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
          LocalDate data,
      @Parameter(
              description = "Filtra opcionalmente a consulta pelo distrito da farmácia.",
              example = "Primeiro Distrito")
          @RequestParam(required = false)
          @Pattern(regexp = ".*\\S.*", message = "O distrito não pode ser vazio.")
          String distrito) {

    ConsultaPlantaoAtualRespostaDTO resposta =
        plantaoService.consultarPlantaoPorData(data, distrito);

    return ResponseEntity.ok(RespostaApiDTO.sucesso(resposta.mensagem(), resposta));
  }

  @Operation(
      summary = "Consultar plantão atual",
      description =
          """
          Retorna as farmácias de plantão considerando
          a regra temporal oficial do sistema:

          07:00 de um dia até 07:00 do dia seguinte.
          """)
  @ApiResponse(
      responseCode = "200",
      description = "Consulta realizada com sucesso.",
      content = @Content(schema = @Schema(implementation = RespostaApiDTO.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Parâmetro inválido na requisição.",
      content = @Content(schema = @Schema(implementation = RespostaApiDTO.class)))
  @ApiResponse(
      responseCode = "500",
      description = "Erro interno inesperado.",
      content = @Content(schema = @Schema(implementation = RespostaApiDTO.class)))
  @GetMapping("/atual")
  public ResponseEntity<RespostaApiDTO<ConsultaPlantaoAtualRespostaDTO>> buscarPlantaoAtual(
      @Parameter(
              description = "Filtra opcionalmente a consulta pelo distrito da farmácia.",
              example = "Segundo Distrito")
          @RequestParam(required = false)
          @Pattern(regexp = ".*\\S.*", message = "O distrito não pode ser vazio.")
          String distrito) {

    ConsultaPlantaoAtualRespostaDTO resposta = plantaoService.consultarPlantaoAtual(distrito);

    return ResponseEntity.ok(RespostaApiDTO.sucesso(resposta.mensagem(), resposta));
  }
}
