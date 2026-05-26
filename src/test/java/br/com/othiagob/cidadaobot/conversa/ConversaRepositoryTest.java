package br.com.othiagob.cidadaobot.conversa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class ConversaRepositoryTest {

  @Autowired private ConversaRepository conversaRepository;

  @Test
  void deveSalvarConversaComSucesso() {
    Conversa conversa =
        new Conversa(
            "5569999999999",
            "Qual farmácia está de plantão hoje?",
            "Hoje a farmácia de plantão é FARMACIA REAL.",
            "CONSULTAR_PLANTAO_ATUAL",
            "Primeiro Distrito",
            LocalDate.of(2026, 5, 24),
            "WHATSAPP");

    Conversa conversaSalva = conversaRepository.saveAndFlush(conversa);

    assertNotNull(conversaSalva.getId());
    assertEquals("5569999999999", conversaSalva.getTelefoneUsuario());
    assertEquals("Qual farmácia está de plantão hoje?", conversaSalva.getMensagemUsuario());
    assertEquals("Hoje a farmácia de plantão é FARMACIA REAL.", conversaSalva.getRespostaEnviada());
    assertEquals("CONSULTAR_PLANTAO_ATUAL", conversaSalva.getIntencaoDetectada());
    assertEquals("Primeiro Distrito", conversaSalva.getDistritoDetectado());
    assertEquals(LocalDate.of(2026, 5, 24), conversaSalva.getDataReferencia());
    assertEquals("WHATSAPP", conversaSalva.getOrigem());
    assertNotNull(conversaSalva.getCriadaEm());
  }

  @Test
  void deveBuscarHistoricoPorTelefoneOrdenadoPorCriadaEmDesc() throws InterruptedException {
    Conversa conversaAntiga =
        new Conversa(
            "5569999999999",
            "Primeira mensagem",
            "Primeira resposta",
            "CONSULTAR_PLANTAO_ATUAL",
            "Primeiro Distrito",
            LocalDate.of(2026, 5, 24),
            "WHATSAPP");

    conversaRepository.saveAndFlush(conversaAntiga);

    Thread.sleep(10);

    Conversa conversaRecente =
        new Conversa(
            "5569999999999",
            "Segunda mensagem",
            "Segunda resposta",
            "CONSULTAR_PLANTAO_ATUAL",
            "Primeiro Distrito",
            LocalDate.of(2026, 5, 25),
            "WHATSAPP");

    conversaRepository.saveAndFlush(conversaRecente);

    List<Conversa> historico =
        conversaRepository.findByTelefoneUsuarioOrderByCriadaEmDesc("5569999999999");

    assertEquals(2, historico.size());
    assertEquals("Segunda mensagem", historico.get(0).getMensagemUsuario());
    assertEquals("Primeira mensagem", historico.get(1).getMensagemUsuario());
  }

  @Test
  void deveBuscarHistoricoPorTelefoneComLimite() throws InterruptedException {
    Conversa primeiraConversa =
        new Conversa(
            "5569999999999",
            "Mensagem 1",
            "Resposta 1",
            "CONSULTAR_PLANTAO_ATUAL",
            "Primeiro Distrito",
            LocalDate.of(2026, 5, 24),
            "WHATSAPP");

    conversaRepository.saveAndFlush(primeiraConversa);

    Thread.sleep(10);

    Conversa segundaConversa =
        new Conversa(
            "5569999999999",
            "Mensagem 2",
            "Resposta 2",
            "CONSULTAR_PLANTAO_ATUAL",
            "Primeiro Distrito",
            LocalDate.of(2026, 5, 25),
            "WHATSAPP");

    conversaRepository.saveAndFlush(segundaConversa);

    List<Conversa> historico =
        conversaRepository.findByTelefoneUsuarioOrderByCriadaEmDesc(
            "5569999999999", PageRequest.of(0, 1));

    assertEquals(1, historico.size());
    assertEquals("Mensagem 2", historico.get(0).getMensagemUsuario());
  }

  @Test
  void deveRetornarListaVaziaQuandoTelefoneNaoPossuirHistorico() {
    List<Conversa> historico =
        conversaRepository.findByTelefoneUsuarioOrderByCriadaEmDesc("5500000000000");

    assertEquals(0, historico.size());
  }

  @Test
  void naoDeveMisturarHistoricoDeTelefonesDiferentes() {
    Conversa conversaUsuarioUm =
        new Conversa(
            "5569999999999",
            "Mensagem usuário 1",
            "Resposta usuário 1",
            "CONSULTAR_PLANTAO_ATUAL",
            "Primeiro Distrito",
            LocalDate.of(2026, 5, 24),
            "WHATSAPP");

    Conversa conversaUsuarioDois =
        new Conversa(
            "5569888888888",
            "Mensagem usuário 2",
            "Resposta usuário 2",
            "CONSULTAR_PLANTAO_ATUAL",
            "Segundo Distrito",
            LocalDate.of(2026, 5, 24),
            "WHATSAPP");

    conversaRepository.saveAndFlush(conversaUsuarioUm);
    conversaRepository.saveAndFlush(conversaUsuarioDois);

    List<Conversa> historicoUsuarioUm =
        conversaRepository.findByTelefoneUsuarioOrderByCriadaEmDesc("5569999999999");

    assertEquals(1, historicoUsuarioUm.size());
    assertEquals("Mensagem usuário 1", historicoUsuarioUm.get(0).getMensagemUsuario());
    assertEquals("5569999999999", historicoUsuarioUm.get(0).getTelefoneUsuario());
  }
}
