package br.com.othiagob.cidadaobot.conversa;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.othiagob.cidadaobot.conversa.dto.ConversaRegistroRequestDTO;
import br.com.othiagob.cidadaobot.conversa.dto.ConversaRespostaDTO;
import br.com.othiagob.cidadaobot.erro.TratadorGlobalDeErros;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ConversaController.class)
@Import(TratadorGlobalDeErros.class)
class ConversaControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private ConversaService conversaService;

  @Test
  void deveRegistrarInteracaoComSucesso() throws Exception {
    ConversaRespostaDTO respostaDTO =
        new ConversaRespostaDTO(
            1L,
            "5569999999999",
            "Qual farmácia está de plantão hoje?",
            "Hoje a farmácia de plantão é FARMACIA REAL.",
            "CONSULTAR_PLANTAO_ATUAL",
            "Primeiro Distrito",
            LocalDate.of(2026, 5, 24),
            "WHATSAPP",
            LocalDateTime.of(2026, 5, 25, 22, 50));

    when(conversaService.registrarInteracao(any(ConversaRegistroRequestDTO.class)))
        .thenReturn(respostaDTO);

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
        .perform(post("/api/conversas").contentType("application/json").content(jsonRequest))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.sucesso").value(true))
        .andExpect(jsonPath("$.mensagem").value("Interação registrada com sucesso."))
        .andExpect(jsonPath("$.dados.id").value(1))
        .andExpect(jsonPath("$.dados.telefoneUsuario").value("5569999999999"))
        .andExpect(jsonPath("$.dados.mensagemUsuario").value("Qual farmácia está de plantão hoje?"))
        .andExpect(
            jsonPath("$.dados.respostaEnviada")
                .value("Hoje a farmácia de plantão é FARMACIA REAL."))
        .andExpect(jsonPath("$.dados.intencaoDetectada").value("CONSULTAR_PLANTAO_ATUAL"))
        .andExpect(jsonPath("$.dados.distritoDetectado").value("Primeiro Distrito"))
        .andExpect(jsonPath("$.dados.dataReferencia").value("2026-05-24"))
        .andExpect(jsonPath("$.dados.origem").value("WHATSAPP"));

    ArgumentCaptor<ConversaRegistroRequestDTO> captor =
        ArgumentCaptor.forClass(ConversaRegistroRequestDTO.class);

    verify(conversaService).registrarInteracao(captor.capture());

    ConversaRegistroRequestDTO requestDTO = captor.getValue();

    org.junit.jupiter.api.Assertions.assertEquals("5569999999999", requestDTO.telefoneUsuario());
    org.junit.jupiter.api.Assertions.assertEquals(
        "Qual farmácia está de plantão hoje?", requestDTO.mensagemUsuario());
    org.junit.jupiter.api.Assertions.assertEquals(
        "Hoje a farmácia de plantão é FARMACIA REAL.", requestDTO.respostaEnviada());
    org.junit.jupiter.api.Assertions.assertEquals(
        "CONSULTAR_PLANTAO_ATUAL", requestDTO.intencaoDetectada());
    org.junit.jupiter.api.Assertions.assertEquals(
        "Primeiro Distrito", requestDTO.distritoDetectado());
    org.junit.jupiter.api.Assertions.assertEquals(
        LocalDate.of(2026, 5, 24), requestDTO.dataReferencia());
    org.junit.jupiter.api.Assertions.assertEquals("WHATSAPP", requestDTO.origem());
  }

  @Test
  void deveRetornarErroQuandoRegistrarInteracaoComCamposObrigatoriosVazios() throws Exception {
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
        .perform(post("/api/conversas").contentType("application/json").content(jsonRequest))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.sucesso").value(false))
        .andExpect(jsonPath("$.dados").doesNotExist());

    verifyNoInteractions(conversaService);
  }

  @Test
  void deveConsultarHistoricoPorTelefoneComSucesso() throws Exception {
    ConversaRespostaDTO respostaDTO =
        new ConversaRespostaDTO(
            1L,
            "5569999999999",
            "Mensagem do usuário",
            "Resposta enviada",
            "CONSULTAR_PLANTAO_ATUAL",
            "Primeiro Distrito",
            LocalDate.of(2026, 5, 24),
            "WHATSAPP",
            LocalDateTime.of(2026, 5, 25, 22, 50));

    when(conversaService.consultarHistoricoPorTelefone("5569999999999", 10))
        .thenReturn(List.of(respostaDTO));

    mockMvc
        .perform(get("/api/conversas").param("telefone", "5569999999999").param("limite", "10"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.sucesso").value(true))
        .andExpect(jsonPath("$.mensagem").value("Histórico de conversas consultado com sucesso."))
        .andExpect(jsonPath("$.dados[0].id").value(1))
        .andExpect(jsonPath("$.dados[0].telefoneUsuario").value("5569999999999"))
        .andExpect(jsonPath("$.dados[0].mensagemUsuario").value("Mensagem do usuário"))
        .andExpect(jsonPath("$.dados[0].respostaEnviada").value("Resposta enviada"))
        .andExpect(jsonPath("$.dados[0].intencaoDetectada").value("CONSULTAR_PLANTAO_ATUAL"))
        .andExpect(jsonPath("$.dados[0].distritoDetectado").value("Primeiro Distrito"))
        .andExpect(jsonPath("$.dados[0].dataReferencia").value("2026-05-24"))
        .andExpect(jsonPath("$.dados[0].origem").value("WHATSAPP"));

    verify(conversaService).consultarHistoricoPorTelefone("5569999999999", 10);
  }

  @Test
  void deveConsultarHistoricoPorTelefoneSemLimiteInformado() throws Exception {
    when(conversaService.consultarHistoricoPorTelefone("5569999999999", null))
        .thenReturn(List.of());

    mockMvc
        .perform(get("/api/conversas").param("telefone", "5569999999999"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.sucesso").value(true))
        .andExpect(jsonPath("$.mensagem").value("Histórico de conversas consultado com sucesso."))
        .andExpect(jsonPath("$.dados").isArray());

    verify(conversaService).consultarHistoricoPorTelefone("5569999999999", null);
  }

  @Test
  void deveRetornarErroQuandoTelefoneNaoForInformadoNaConsulta() throws Exception {
    mockMvc
        .perform(get("/api/conversas").param("limite", "10"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.sucesso").value(false));

    verifyNoInteractions(conversaService);
  }

  @Test
  void deveRetornarErroQuandoTelefoneEstiverVazioNaConsulta() throws Exception {
    mockMvc
        .perform(get("/api/conversas").param("telefone", "   ").param("limite", "10"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.sucesso").value(false))
        .andExpect(jsonPath("$.mensagem").value("Telefone do usuário é obrigatório."));

    verifyNoInteractions(conversaService);
  }

  @Test
  void deveRetornarErroQuandoLimiteForInvalido() throws Exception {
    when(conversaService.consultarHistoricoPorTelefone(eq("5569999999999"), eq(0)))
        .thenThrow(new IllegalArgumentException("Limite deve ser maior que zero."));

    mockMvc
        .perform(get("/api/conversas").param("telefone", "5569999999999").param("limite", "0"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.sucesso").value(false))
        .andExpect(jsonPath("$.mensagem").value("Limite deve ser maior que zero."));

    verify(conversaService).consultarHistoricoPorTelefone("5569999999999", 0);
  }
}
