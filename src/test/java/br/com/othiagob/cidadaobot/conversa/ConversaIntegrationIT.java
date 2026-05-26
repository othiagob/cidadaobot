package br.com.othiagob.cidadaobot.conversa;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
class ConversaIntegrationIT {

  @Autowired private MockMvc mockMvc;

  @Test
  void deveRegistrarEConsultarHistoricoDeConversasComSucesso() throws Exception {
    String jsonRequest =
        """
        {
          "telefoneUsuario": "5569999999999",
          "mensagemUsuario": "Qual farmácia está de plantão hoje?",
          "respostaEnviada": "Hoje a farmácia de plantão é FARMACIA REAL.",
          "intencaoDetectada": "CONSULTAR_PLANTAO_ATUAL",
          "distritoDetectado": "Primeiro Distrito",
          "dataReferencia": "2026-05-24",
          "origem": "WHATSAPP"
        }
        """;

    mockMvc
        .perform(
            post("/api/conversas").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.sucesso").value(true))
        .andExpect(jsonPath("$.mensagem").value("Interação registrada com sucesso."))
        .andExpect(jsonPath("$.dados.id").exists())
        .andExpect(jsonPath("$.dados.telefoneUsuario").value("5569999999999"))
        .andExpect(jsonPath("$.dados.mensagemUsuario").value("Qual farmácia está de plantão hoje?"))
        .andExpect(
            jsonPath("$.dados.respostaEnviada")
                .value("Hoje a farmácia de plantão é FARMACIA REAL."))
        .andExpect(jsonPath("$.dados.intencaoDetectada").value("CONSULTAR_PLANTAO_ATUAL"))
        .andExpect(jsonPath("$.dados.distritoDetectado").value("Primeiro Distrito"))
        .andExpect(jsonPath("$.dados.dataReferencia").value("2026-05-24"))
        .andExpect(jsonPath("$.dados.origem").value("WHATSAPP"))
        .andExpect(jsonPath("$.dados.criadaEm").exists());

    mockMvc
        .perform(get("/api/conversas").param("telefone", "5569999999999").param("limite", "10"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.sucesso").value(true))
        .andExpect(jsonPath("$.mensagem").value("Histórico de conversas consultado com sucesso."))
        .andExpect(jsonPath("$.dados").isArray())
        .andExpect(jsonPath("$.dados[0].telefoneUsuario").value("5569999999999"))
        .andExpect(
            jsonPath("$.dados[0].mensagemUsuario").value("Qual farmácia está de plantão hoje?"))
        .andExpect(
            jsonPath("$.dados[0].respostaEnviada")
                .value("Hoje a farmácia de plantão é FARMACIA REAL."))
        .andExpect(jsonPath("$.dados[0].origem").value("WHATSAPP"));
  }

  @Test
  void deveRetornarErroAoRegistrarConversaComDadosInvalidos() throws Exception {
    String jsonRequest =
        """
        {
          "telefoneUsuario": "",
          "mensagemUsuario": "",
          "respostaEnviada": "",
          "origem": ""
        }
        """;

    mockMvc
        .perform(
            post("/api/conversas").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.sucesso").value(false))
        .andExpect(jsonPath("$.mensagem").exists());
  }

  @Test
  void deveRetornarErroAoConsultarHistoricoComLimiteInvalido() throws Exception {
    mockMvc
        .perform(get("/api/conversas").param("telefone", "5569999999999").param("limite", "0"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.sucesso").value(false))
        .andExpect(jsonPath("$.mensagem").value("Limite deve ser maior que zero."));
  }
}
