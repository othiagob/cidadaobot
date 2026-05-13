package br.com.othiagob.cidadaobot.plantao;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.othiagob.cidadaobot.farmacia.Farmacia;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PlantaoController.class)
class PlantaoControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private PlantaoService plantaoService;

  @Test
  @DisplayName("Deve retornar plantão atual quando existir escala cadastrada")
  void deveRetornarPlantaoAtualQuandoExistirEscala() throws Exception {
    LocalDate dataPlantao = LocalDate.of(2026, 5, 10);

    Farmacia farmacia =
        new Farmacia(
            "Farmácia Central",
            "Av. Brasil, 123",
            "Centro",
            "Primeiro Distrito",
            "(69) 99999-9999");

    EscalaPlantao escala =
        new EscalaPlantao(farmacia, dataPlantao, LocalTime.of(19, 0), LocalTime.of(7, 0), null);

    when(plantaoService.determinarDataPlantaoAtivo(any(LocalDateTime.class)))
        .thenReturn(Optional.of(dataPlantao));

    when(plantaoService.buscarPlantoesAtivos(any(LocalDateTime.class))).thenReturn(List.of(escala));

    mockMvc
        .perform(get("/api/plantoes/atual"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.dataReferencia").value("2026-05-10"))
        .andExpect(jsonPath("$.plantaoAtivo").value(true))
        .andExpect(jsonPath("$.mensagem").value("Plantão ativo encontrado."))
        .andExpect(jsonPath("$.plantoes", hasSize(1)))
        .andExpect(jsonPath("$.plantoes[0].dataPlantao").value("2026-05-10"))
        .andExpect(jsonPath("$.plantoes[0].iniciaAs").value("19:00:00"))
        .andExpect(jsonPath("$.plantoes[0].terminaAs").value("07:00:00"))
        .andExpect(jsonPath("$.plantoes[0].farmacia.nome").value("Farmácia Central"))
        .andExpect(jsonPath("$.plantoes[0].farmacia.distrito").value("Primeiro Distrito"));
  }

  @Test
  @DisplayName("Deve retornar lista vazia quando estiver fora do horário de plantão")
  void deveRetornarListaVaziaQuandoEstiverForaDoHorarioDePlantao() throws Exception {
    when(plantaoService.determinarDataPlantaoAtivo(any(LocalDateTime.class)))
        .thenReturn(Optional.empty());

    mockMvc
        .perform(get("/api/plantoes/atual"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.dataReferencia").doesNotExist())
        .andExpect(jsonPath("$.plantaoAtivo").value(false))
        .andExpect(
            jsonPath("$.mensagem")
                .value(
                    "Não há plantão ativo neste horário. O plantão funciona das 19:00 às 07:00."))
        .andExpect(jsonPath("$.plantoes", hasSize(0)));
  }

  @Test
  @DisplayName("Deve retornar mensagem clara quando não houver escala cadastrada")
  void deveRetornarMensagemQuandoNaoHouverEscalaCadastrada() throws Exception {
    LocalDate dataPlantao = LocalDate.of(2026, 5, 10);

    when(plantaoService.determinarDataPlantaoAtivo(any(LocalDateTime.class)))
        .thenReturn(Optional.of(dataPlantao));

    when(plantaoService.buscarPlantoesAtivos(any(LocalDateTime.class))).thenReturn(List.of());

    mockMvc
        .perform(get("/api/plantoes/atual"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.dataReferencia").value("2026-05-10"))
        .andExpect(jsonPath("$.plantaoAtivo").value(true))
        .andExpect(
            jsonPath("$.mensagem")
                .value("Não encontrei escala de plantão cadastrada para esta data no sistema."))
        .andExpect(jsonPath("$.plantoes", hasSize(0)));
  }

  @Test
  @DisplayName("Deve filtrar plantão atual por distrito")
  void deveFiltrarPlantaoAtualPorDistrito() throws Exception {
    LocalDate dataPlantao = LocalDate.of(2026, 5, 10);

    Farmacia farmacia =
        new Farmacia(
            "Farmácia Segundo Distrito",
            "Rua T-10, 500",
            "Nova Brasília",
            "Segundo Distrito",
            "(69) 98888-8888");

    EscalaPlantao escala =
        new EscalaPlantao(farmacia, dataPlantao, LocalTime.of(19, 0), LocalTime.of(7, 0), null);

    when(plantaoService.determinarDataPlantaoAtivo(any(LocalDateTime.class)))
        .thenReturn(Optional.of(dataPlantao));

    when(plantaoService.buscarPlantoesAtivosPorDistrito(
            any(LocalDateTime.class), eq("Segundo Distrito")))
        .thenReturn(List.of(escala));

    mockMvc
        .perform(get("/api/plantoes/atual").param("distrito", "Segundo Distrito"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.plantaoAtivo").value(true))
        .andExpect(jsonPath("$.plantoes", hasSize(1)))
        .andExpect(jsonPath("$.plantoes[0].farmacia.nome").value("Farmácia Segundo Distrito"))
        .andExpect(jsonPath("$.plantoes[0].farmacia.distrito").value("Segundo Distrito"));
  }
}
