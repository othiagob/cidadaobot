package br.com.othiagob.cidadaobot.conversa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.othiagob.cidadaobot.conversa.dto.ConversaRegistroRequestDTO;
import br.com.othiagob.cidadaobot.conversa.dto.ConversaRespostaDTO;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Pageable;

class ConversaServiceTest {

  private ConversaRepository conversaRepository;
  private ConversaService conversaService;

  @BeforeEach
  void setUp() {
    conversaRepository = org.mockito.Mockito.mock(ConversaRepository.class);
    conversaService = new ConversaService(conversaRepository);
  }

  @Test
  void deveRegistrarInteracaoComSucesso() {
    ConversaRegistroRequestDTO requestDTO =
        new ConversaRegistroRequestDTO(
            "5569999999999",
            "Qual farmácia está de plantão hoje?",
            "Hoje a farmácia de plantão é FARMACIA REAL.",
            "CONSULTAR_PLANTAO_ATUAL",
            "Primeiro Distrito",
            LocalDate.of(2026, 5, 24),
            "WHATSAPP");

    Conversa conversaSalva =
        criarConversaComIdECriadaEm(
            1L,
            "5569999999999",
            "Qual farmácia está de plantão hoje?",
            "Hoje a farmácia de plantão é FARMACIA REAL.",
            "CONSULTAR_PLANTAO_ATUAL",
            "Primeiro Distrito",
            LocalDate.of(2026, 5, 24),
            "WHATSAPP",
            LocalDateTime.of(2026, 5, 25, 22, 45));

    when(conversaRepository.save(any(Conversa.class))).thenReturn(conversaSalva);

    ConversaRespostaDTO resposta = conversaService.registrarInteracao(requestDTO);

    assertEquals(1L, resposta.id());
    assertEquals("5569999999999", resposta.telefoneUsuario());
    assertEquals("Qual farmácia está de plantão hoje?", resposta.mensagemUsuario());
    assertEquals("Hoje a farmácia de plantão é FARMACIA REAL.", resposta.respostaEnviada());
    assertEquals("CONSULTAR_PLANTAO_ATUAL", resposta.intencaoDetectada());
    assertEquals("Primeiro Distrito", resposta.distritoDetectado());
    assertEquals(LocalDate.of(2026, 5, 24), resposta.dataReferencia());
    assertEquals("WHATSAPP", resposta.origem());
    assertEquals(LocalDateTime.of(2026, 5, 25, 22, 45), resposta.criadaEm());

    ArgumentCaptor<Conversa> conversaCaptor = ArgumentCaptor.forClass(Conversa.class);
    verify(conversaRepository).save(conversaCaptor.capture());

    Conversa conversaEnviadaParaSalvar = conversaCaptor.getValue();

    assertEquals("5569999999999", conversaEnviadaParaSalvar.getTelefoneUsuario());
    assertEquals(
        "Qual farmácia está de plantão hoje?", conversaEnviadaParaSalvar.getMensagemUsuario());
    assertEquals(
        "Hoje a farmácia de plantão é FARMACIA REAL.",
        conversaEnviadaParaSalvar.getRespostaEnviada());
    assertEquals("CONSULTAR_PLANTAO_ATUAL", conversaEnviadaParaSalvar.getIntencaoDetectada());
    assertEquals("Primeiro Distrito", conversaEnviadaParaSalvar.getDistritoDetectado());
    assertEquals(LocalDate.of(2026, 5, 24), conversaEnviadaParaSalvar.getDataReferencia());
    assertEquals("WHATSAPP", conversaEnviadaParaSalvar.getOrigem());
  }

  @Test
  void deveConsultarHistoricoPorTelefoneComLimiteInformado() {
    Conversa conversa =
        criarConversaComIdECriadaEm(
            1L,
            "5569999999999",
            "Mensagem do usuário",
            "Resposta enviada",
            "CONSULTAR_PLANTAO_ATUAL",
            "Primeiro Distrito",
            LocalDate.of(2026, 5, 24),
            "WHATSAPP",
            LocalDateTime.of(2026, 5, 25, 22, 45));

    when(conversaRepository.findByTelefoneUsuarioOrderByCriadaEmDesc(
            eq("5569999999999"), any(Pageable.class)))
        .thenReturn(List.of(conversa));

    List<ConversaRespostaDTO> historico =
        conversaService.consultarHistoricoPorTelefone("5569999999999", 5);

    assertEquals(1, historico.size());
    assertEquals("5569999999999", historico.get(0).telefoneUsuario());
    assertEquals("Mensagem do usuário", historico.get(0).mensagemUsuario());
    assertEquals("Resposta enviada", historico.get(0).respostaEnviada());
    ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
    verify(conversaRepository)
        .findByTelefoneUsuarioOrderByCriadaEmDesc(eq("5569999999999"), pageableCaptor.capture());

    Pageable pageable = pageableCaptor.getValue();

    assertEquals(0, pageable.getPageNumber());
    assertEquals(5, pageable.getPageSize());
  }

  @Test
  void deveConsultarHistoricoPorTelefoneComLimitePadraoQuandoLimiteForNulo() {
    when(conversaRepository.findByTelefoneUsuarioOrderByCriadaEmDesc(
            eq("5569999999999"), any(Pageable.class)))
        .thenReturn(List.of());

    List<ConversaRespostaDTO> historico =
        conversaService.consultarHistoricoPorTelefone("5569999999999", null);

    assertEquals(0, historico.size());

    ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
    verify(conversaRepository)
        .findByTelefoneUsuarioOrderByCriadaEmDesc(eq("5569999999999"), pageableCaptor.capture());

    Pageable pageable = pageableCaptor.getValue();

    assertEquals(0, pageable.getPageNumber());
    assertEquals(10, pageable.getPageSize());
  }

  @Test
  void deveLancarErroQuandoLimiteForMenorQueUm() {
    IllegalArgumentException excecao =
        assertThrows(
            IllegalArgumentException.class,
            () -> conversaService.consultarHistoricoPorTelefone("5569999999999", 0));

    assertEquals("Limite deve ser maior que zero.", excecao.getMessage());
  }

  @Test
  void deveLancarErroQuandoLimiteForMaiorQueCem() {
    IllegalArgumentException excecao =
        assertThrows(
            IllegalArgumentException.class,
            () -> conversaService.consultarHistoricoPorTelefone("5569999999999", 101));

    assertEquals("Limite máximo permitido é 100.", excecao.getMessage());
  }

  private Conversa criarConversaComIdECriadaEm(
      Long id,
      String telefoneUsuario,
      String mensagemUsuario,
      String respostaEnviada,
      String intencaoDetectada,
      String distritoDetectado,
      LocalDate dataReferencia,
      String origem,
      LocalDateTime criadaEm) {
    Conversa conversa =
        new Conversa(
            telefoneUsuario,
            mensagemUsuario,
            respostaEnviada,
            intencaoDetectada,
            distritoDetectado,
            dataReferencia,
            origem);

    definirCampoPrivado(conversa, "id", id);
    definirCampoPrivado(conversa, "criadaEm", criadaEm);

    return conversa;
  }

  private void definirCampoPrivado(Object objeto, String nomeCampo, Object valor) {
    try {
      Field campo = objeto.getClass().getDeclaredField(nomeCampo);
      campo.setAccessible(true);
      campo.set(objeto, valor);
    } catch (NoSuchFieldException | IllegalAccessException ex) {
      throw new IllegalStateException("Erro ao configurar campo privado no teste.", ex);
    }
  }
}
