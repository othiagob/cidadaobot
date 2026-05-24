package br.com.othiagob.cidadaobot.plantao;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
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
            LocalTime.of(7, 0),
            LocalTime.of(7, 0),
            new FarmaciaRespostaDTO(
                "Farmácia Central",
                "Av. Brasil, 123",
                "Centro",
                "Primeiro Distrito",
                "(69) 99999-9999"),
            "Farmácia Central está de plantão das 07:00 às 07:00.");

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
        .andExpect(jsonPath("$.dados.plantoes[0].iniciaAs").value("07:00:00"))
        .andExpect(jsonPath("$.dados.plantoes[0].terminaAs").value("07:00:00"))
        .andExpect(jsonPath("$.dados.plantoes[0].farmacia.nome").value("Farmácia Central"))
        .andExpect(jsonPath("$.dados.plantoes[0].farmacia.distrito").value("Primeiro Distrito"))
        .andExpect(jsonPath("$.timestamp").isNotEmpty());
  }

  @Test
  @DisplayName("Deve retornar lista vazia quando não houver escala cadastrada para o plantão atual")
  void deveRetornarListaVaziaQuandoNaoHouverEscalaCadastradaParaPlantaoAtual() throws Exception {
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
        .andExpect(
            jsonPath("$.mensagem")
                .value("Não encontrei escala de plantão cadastrada para esta data no sistema."))
        .andExpect(jsonPath("$.dados.dataReferencia").value("2026-05-10"))
        .andExpect(jsonPath("$.dados.plantaoAtivo").value(true))
        .andExpect(
            jsonPath("$.dados.mensagem")
                .value("Não encontrei escala de plantão cadastrada para esta data no sistema."))
        .andExpect(jsonPath("$.dados.plantoes", hasSize(0)))
        .andExpect(jsonPath("$.timestamp").isNotEmpty());
  }

  @Test
  @DisplayName("Deve retornar erro padronizado quando distrito do plantão atual for inválido")
  void deveRetornarErroQuandoDistritoForInvalido() throws Exception {
    mockMvc
        .perform(get("/api/plantoes/atual").param("distrito", "   "))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.sucesso").value(false))
        .andExpect(jsonPath("$.mensagem", containsString("O distrito não pode ser vazio.")))
        .andExpect(jsonPath("$.dados").isEmpty())
        .andExpect(jsonPath("$.timestamp").isNotEmpty());
  }

  @Test
  @DisplayName("Deve filtrar plantão atual por distrito")
  void deveFiltrarPlantaoAtualPorDistrito() throws Exception {
    LocalDate dataPlantao = LocalDate.of(2026, 5, 10);
    String distrito = "Segundo Distrito";

    PlantaoRespostaDTO plantao =
        new PlantaoRespostaDTO(
            dataPlantao,
            LocalTime.of(7, 0),
            LocalTime.of(7, 0),
            new FarmaciaRespostaDTO(
                "Farmácia Segundo Distrito",
                "Rua T-10, 500",
                "Nova Brasília",
                distrito,
                "(69) 98888-8888"),
            "Farmácia Segundo Distrito está de plantão das 07:00 às 07:00.");

    ConsultaPlantaoAtualRespostaDTO resposta =
        new ConsultaPlantaoAtualRespostaDTO(
            dataPlantao, true, "Plantão ativo encontrado.", List.of(plantao));

    when(plantaoService.consultarPlantaoAtual(distrito)).thenReturn(resposta);

    mockMvc
        .perform(get("/api/plantoes/atual").param("distrito", distrito))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.sucesso").value(true))
        .andExpect(jsonPath("$.mensagem").value("Plantão ativo encontrado."))
        .andExpect(jsonPath("$.dados.dataReferencia").value("2026-05-10"))
        .andExpect(jsonPath("$.dados.plantaoAtivo").value(true))
        .andExpect(jsonPath("$.dados.plantoes", hasSize(1)))
        .andExpect(jsonPath("$.dados.plantoes[0].farmacia.nome").value("Farmácia Segundo Distrito"))
        .andExpect(jsonPath("$.dados.plantoes[0].farmacia.distrito").value(distrito))
        .andExpect(jsonPath("$.timestamp").isNotEmpty());
  }

  @Test
  @DisplayName("Deve consultar plantão por data")
  void deveConsultarPlantaoPorData() throws Exception {
    LocalDate dataPlantao = LocalDate.of(2026, 5, 17);

    PlantaoRespostaDTO plantao =
        new PlantaoRespostaDTO(
            dataPlantao,
            LocalTime.of(7, 0),
            LocalTime.of(7, 0),
            new FarmaciaRespostaDTO(
                "Farmácia Real",
                "Rua dos Mineiros, 298",
                "Centro",
                "Primeiro Distrito",
                "3422-3491"),
            "Farmácia Real está de plantão das 07:00 às 07:00.");

    ConsultaPlantaoAtualRespostaDTO resposta =
        new ConsultaPlantaoAtualRespostaDTO(
            dataPlantao, true, "Plantão encontrado para a data informada.", List.of(plantao));

    when(plantaoService.consultarPlantaoPorData(dataPlantao, null)).thenReturn(resposta);

    mockMvc
        .perform(get("/api/plantoes").param("data", "2026-05-17"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.sucesso").value(true))
        .andExpect(jsonPath("$.mensagem").value("Plantão encontrado para a data informada."))
        .andExpect(jsonPath("$.dados.dataReferencia").value("2026-05-17"))
        .andExpect(jsonPath("$.dados.plantaoAtivo").value(true))
        .andExpect(jsonPath("$.dados.plantoes", hasSize(1)))
        .andExpect(jsonPath("$.dados.plantoes[0].farmacia.nome").value("Farmácia Real"))
        .andExpect(jsonPath("$.dados.plantoes[0].farmacia.distrito").value("Primeiro Distrito"))
        .andExpect(jsonPath("$.timestamp").isNotEmpty());
  }

  @Test
  @DisplayName("Deve consultar plantão por data e distrito")
  void deveConsultarPlantaoPorDataEDistrito() throws Exception {
    LocalDate dataPlantao = LocalDate.of(2026, 5, 17);
    String distrito = "Segundo Distrito";

    PlantaoRespostaDTO plantao =
        new PlantaoRespostaDTO(
            dataPlantao,
            LocalTime.of(7, 0),
            LocalTime.of(7, 0),
            new FarmaciaRespostaDTO(
                "Saúde Popular", "Av. Brasil, 2000", "Nova Brasília", distrito, "3421-0000"),
            "Saúde Popular está de plantão das 07:00 às 07:00.");

    ConsultaPlantaoAtualRespostaDTO resposta =
        new ConsultaPlantaoAtualRespostaDTO(
            dataPlantao, true, "Plantão encontrado para a data informada.", List.of(plantao));

    when(plantaoService.consultarPlantaoPorData(dataPlantao, distrito)).thenReturn(resposta);

    mockMvc
        .perform(get("/api/plantoes").param("data", "2026-05-17").param("distrito", distrito))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.sucesso").value(true))
        .andExpect(jsonPath("$.mensagem").value("Plantão encontrado para a data informada."))
        .andExpect(jsonPath("$.dados.dataReferencia").value("2026-05-17"))
        .andExpect(jsonPath("$.dados.plantoes", hasSize(1)))
        .andExpect(jsonPath("$.dados.plantoes[0].farmacia.nome").value("Saúde Popular"))
        .andExpect(jsonPath("$.dados.plantoes[0].farmacia.distrito").value(distrito))
        .andExpect(jsonPath("$.timestamp").isNotEmpty());
  }

  @Test
  @DisplayName("Deve retornar erro padronizado quando data não for informada")
  void deveRetornarErroQuandoDataNaoForInformada() throws Exception {
    mockMvc
        .perform(get("/api/plantoes"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.sucesso").value(false))
        .andExpect(jsonPath("$.mensagem").value("Parâmetro obrigatório ausente: data."))
        .andExpect(jsonPath("$.dados").isEmpty())
        .andExpect(jsonPath("$.timestamp").isNotEmpty());
  }

  @Test
  @DisplayName("Deve retornar erro padronizado quando data estiver em formato inválido")
  void deveRetornarErroQuandoDataEstiverEmFormatoInvalido() throws Exception {
    mockMvc
        .perform(get("/api/plantoes").param("data", "17-05-2026"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.sucesso").value(false))
        .andExpect(jsonPath("$.mensagem").value("Data inválida. Formato esperado: AAAA-MM-DD."))
        .andExpect(jsonPath("$.dados").isEmpty())
        .andExpect(jsonPath("$.timestamp").isNotEmpty());
  }

  @Test
  @DisplayName("Deve retornar erro padronizado quando distrito da consulta por data for inválido")
  void deveRetornarErroQuandoDistritoDaConsultaPorDataForInvalido() throws Exception {
    mockMvc
        .perform(get("/api/plantoes").param("data", "2026-05-17").param("distrito", "   "))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.sucesso").value(false))
        .andExpect(jsonPath("$.mensagem", containsString("O distrito não pode ser vazio.")))
        .andExpect(jsonPath("$.dados").isEmpty())
        .andExpect(jsonPath("$.timestamp").isNotEmpty());
  }
}
