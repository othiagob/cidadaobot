package br.com.othiagob.cidadaobot.plantao;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.othiagob.cidadaobot.plantao.dto.ConsultaPlantaoAtualRespostaDTO;
import br.com.othiagob.cidadaobot.plantao.dto.FarmaciaRespostaDTO;
import br.com.othiagob.cidadaobot.plantao.dto.PlantaoRespostaDTO;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
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

    PlantaoRespostaDTO plantao =
        new PlantaoRespostaDTO(
            dataPlantao,
            LocalTime.of(19, 0),
            LocalTime.of(7, 0),
            new FarmaciaRespostaDTO(
                "Farmácia Central",
                "Av. Brasil, 123",
                "Centro",
                "Primeiro Distrito",
                "(69) 99999-9999"),
            "Farmácia Central está de plantão das 19:00 às 07:00.");

    ConsultaPlantaoAtualRespostaDTO resposta =
        new ConsultaPlantaoAtualRespostaDTO(
            dataPlantao, true, "Plantão ativo encontrado.", List.of(plantao));

    when(plantaoService.consultarPlantaoAtual(null)).thenReturn(resposta);

    mockMvc
        .perform(get("/api/plantoes/atual"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.sucesso").value(true))
        .andExpect(jsonPath("$.mensagem").value("Plantão ativo encontrado."))
        .andExpect(jsonPath("$.dados.dataReferencia").value("2026-05-10"))
        .andExpect(jsonPath("$.dados.plantaoAtivo").value(true))
        .andExpect(jsonPath("$.dados.mensagem").value("Plantão ativo encontrado."))
        .andExpect(jsonPath("$.dados.plantoes", hasSize(1)))
        .andExpect(jsonPath("$.dados.plantoes[0].dataPlantao").value("2026-05-10"))
        .andExpect(jsonPath("$.dados.plantoes[0].iniciaAs").value("19:00:00"))
        .andExpect(jsonPath("$.dados.plantoes[0].terminaAs").value("07:00:00"))
        .andExpect(jsonPath("$.dados.plantoes[0].farmacia.nome").value("Farmácia Central"))
        .andExpect(jsonPath("$.dados.plantoes[0].farmacia.distrito").value("Primeiro Distrito"));
  }

  @Test
  @DisplayName("Deve retornar lista vazia quando estiver fora do horário de plantão")
  void deveRetornarListaVaziaQuandoEstiverForaDoHorarioDePlantao() throws Exception {
    ConsultaPlantaoAtualRespostaDTO resposta =
        new ConsultaPlantaoAtualRespostaDTO(
            null,
            false,
            "Não há plantão ativo neste horário. O plantão funciona das 19:00 às 07:00.",
            List.of());

    when(plantaoService.consultarPlantaoAtual(null)).thenReturn(resposta);

    mockMvc
        .perform(get("/api/plantoes/atual"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.sucesso").value(true))
        .andExpect(jsonPath("$.dados.dataReferencia").doesNotExist())
        .andExpect(jsonPath("$.dados.plantaoAtivo").value(false))
        .andExpect(
            jsonPath("$.dados.mensagem")
                .value(
                    "Não há plantão ativo neste horário. O plantão funciona das 19:00 às 07:00."))
        .andExpect(jsonPath("$.dados.plantoes", hasSize(0)));
  }

  @Test
  @DisplayName("Deve retornar mensagem clara quando não houver escala cadastrada")
  void deveRetornarMensagemQuandoNaoHouverEscalaCadastrada() throws Exception {
    LocalDate dataPlantao = LocalDate.of(2026, 5, 10);

    ConsultaPlantaoAtualRespostaDTO resposta =
        new ConsultaPlantaoAtualRespostaDTO(
            dataPlantao,
            true,
            "Não encontrei escala de plantão cadastrada para esta data no sistema.",
            List.of());

    when(plantaoService.consultarPlantaoAtual(null)).thenReturn(resposta);

    mockMvc
        .perform(get("/api/plantoes/atual"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.sucesso").value(true))
        .andExpect(jsonPath("$.dados.dataReferencia").value("2026-05-10"))
        .andExpect(jsonPath("$.dados.plantaoAtivo").value(true))
        .andExpect(
            jsonPath("$.dados.mensagem")
                .value("Não encontrei escala de plantão cadastrada para esta data no sistema."))
        .andExpect(jsonPath("$.dados.plantoes", hasSize(0)));
  }

  @Test
  @DisplayName("Deve retornar erro 400 quando distrito for inválido")
  void deveRetornarErroQuandoDistritoForInvalido() throws Exception {
    when(plantaoService.consultarPlantaoAtual(eq("   ")))
        .thenThrow(new IllegalArgumentException("O distrito não pode ser nulo ou vazio."));

    mockMvc
        .perform(get("/api/plantoes/atual").param("distrito", "   "))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status").value(400))
        .andExpect(jsonPath("$.erro").value("Requisição inválida"))
        .andExpect(jsonPath("$.mensagem").value("O distrito não pode ser nulo ou vazio."))
        .andExpect(jsonPath("$.caminho").value("/api/plantoes/atual"));
  }

  @Test
  @DisplayName("Deve filtrar plantão atual por distrito")
  void deveFiltrarPlantaoAtualPorDistrito() throws Exception {
    LocalDate dataPlantao = LocalDate.of(2026, 5, 10);

    PlantaoRespostaDTO plantao =
        new PlantaoRespostaDTO(
            dataPlantao,
            LocalTime.of(19, 0),
            LocalTime.of(7, 0),
            new FarmaciaRespostaDTO(
                "Farmácia Segundo Distrito",
                "Rua T-10, 500",
                "Nova Brasília",
                "Segundo Distrito",
                "(69) 98888-8888"),
            "Farmácia Segundo Distrito está de plantão das 19:00 às 07:00.");

    ConsultaPlantaoAtualRespostaDTO resposta =
        new ConsultaPlantaoAtualRespostaDTO(
            dataPlantao, true, "Plantão ativo encontrado.", List.of(plantao));

    when(plantaoService.consultarPlantaoAtual(eq("Segundo Distrito"))).thenReturn(resposta);

    mockMvc
        .perform(get("/api/plantoes/atual").param("distrito", "Segundo Distrito"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.sucesso").value(true))
        .andExpect(jsonPath("$.dados.plantaoAtivo").value(true))
        .andExpect(jsonPath("$.dados.plantoes", hasSize(1)))
        .andExpect(jsonPath("$.dados.plantoes[0].farmacia.nome").value("Farmácia Segundo Distrito"))
        .andExpect(jsonPath("$.dados.plantoes[0].farmacia.distrito").value("Segundo Distrito"));
  }
}
