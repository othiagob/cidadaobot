package br.com.othiagob.cidadaobot.plantao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.othiagob.cidadaobot.farmacia.Farmacia;
import br.com.othiagob.cidadaobot.plantao.dto.ConsultaPlantaoAtualRespostaDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PlantaoServiceTest {

  private final EscalaPlantaoRepository escalaPlantaoRepository =
      Mockito.mock(EscalaPlantaoRepository.class);

  private final PlantaoService plantaoService = new PlantaoService(escalaPlantaoRepository);

  @Test
  @DisplayName("Deve determinar a data do plantão no mesmo dia após 07:00")
  void deveDeterminarDataPlantaoMesmoDiaAposSeteHoras() {
    LocalDateTime momento = LocalDateTime.of(2026, 5, 17, 8, 0);

    LocalDate dataPlantao = plantaoService.determinarDataPlantaoAtivo(momento).orElseThrow();

    assertThat(dataPlantao).isEqualTo(LocalDate.of(2026, 5, 17));
  }

  @Test
  @DisplayName("Deve determinar a data do plantão do dia anterior antes de 07:00")
  void deveDeterminarDataPlantaoDiaAnteriorAntesSeteHoras() {
    LocalDateTime momento = LocalDateTime.of(2026, 5, 18, 2, 30);

    LocalDate dataPlantao = plantaoService.determinarDataPlantaoAtivo(momento).orElseThrow();

    assertThat(dataPlantao).isEqualTo(LocalDate.of(2026, 5, 17));
  }

  @Test
  @DisplayName("Deve considerar 07:00 como plantão do próprio dia")
  void deveConsiderarSeteHorasComoPlantaoDoProprioDia() {
    LocalDateTime momento = LocalDateTime.of(2026, 5, 18, 7, 0);

    LocalDate dataPlantao = plantaoService.determinarDataPlantaoAtivo(momento).orElseThrow();

    assertThat(dataPlantao).isEqualTo(LocalDate.of(2026, 5, 18));
  }

  @Test
  @DisplayName("Deve lançar exceção quando momento for nulo")
  void deveLancarExcecaoQuandoMomentoForNulo() {
    assertThatThrownBy(() -> plantaoService.determinarDataPlantaoAtivo(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("O momento da consulta não pode ser nulo.");
  }

  @Test
  @DisplayName("Deve buscar plantões ativos pela data calculada")
  void deveBuscarPlantoesAtivosPelaDataCalculada() {
    LocalDateTime momento = LocalDateTime.of(2026, 5, 18, 2, 30);
    LocalDate dataPlantao = LocalDate.of(2026, 5, 17);

    when(escalaPlantaoRepository.findByDataPlantao(dataPlantao)).thenReturn(List.of());

    List<EscalaPlantao> resultado = plantaoService.buscarPlantoesAtivos(momento);

    assertThat(resultado).isEmpty();
    verify(escalaPlantaoRepository).findByDataPlantao(dataPlantao);
  }

  @Test
  @DisplayName("Deve buscar plantões ativos por distrito")
  void deveBuscarPlantoesAtivosPorDistrito() {
    LocalDateTime momento = LocalDateTime.of(2026, 5, 18, 2, 30);
    LocalDate dataPlantao = LocalDate.of(2026, 5, 17);
    String distrito = "Primeiro Distrito";

    when(escalaPlantaoRepository.findByDataPlantaoAndFarmaciaDistritoIgnoreCase(
            dataPlantao, distrito))
        .thenReturn(List.of());

    List<EscalaPlantao> resultado =
        plantaoService.buscarPlantoesAtivosPorDistrito(momento, distrito);

    assertThat(resultado).isEmpty();

    verify(escalaPlantaoRepository)
        .findByDataPlantaoAndFarmaciaDistritoIgnoreCase(dataPlantao, distrito);
  }

  @Test
  @DisplayName("Deve lançar exceção quando distrito for nulo")
  void deveLancarExcecaoQuandoDistritoForNulo() {
    LocalDateTime momento = LocalDateTime.of(2026, 5, 18, 2, 30);

    assertThatThrownBy(() -> plantaoService.buscarPlantoesAtivosPorDistrito(momento, null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("O distrito não pode ser nulo ou vazio.");
  }

  @Test
  @DisplayName("Deve lançar exceção quando distrito for vazio")
  void deveLancarExcecaoQuandoDistritoForVazio() {
    LocalDateTime momento = LocalDateTime.of(2026, 5, 18, 2, 30);

    assertThatThrownBy(() -> plantaoService.buscarPlantoesAtivosPorDistrito(momento, "   "))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("O distrito não pode ser nulo ou vazio.");
  }

  @Test
  @DisplayName("Deve consultar plantão atual sem distrito")
  void deveConsultarPlantaoAtualSemDistrito() {
    LocalDateTime momento = LocalDateTime.of(2026, 5, 17, 10, 0);
    LocalDate dataPlantao = LocalDate.of(2026, 5, 17);

    Farmacia farmacia = criarFarmacia("Farmácia Central", "Primeiro Distrito");
    EscalaPlantao escala = criarEscala(dataPlantao, farmacia);

    when(escalaPlantaoRepository.findByDataPlantao(dataPlantao)).thenReturn(List.of(escala));

    ConsultaPlantaoAtualRespostaDTO resposta = plantaoService.consultarPlantaoAtual(null, momento);

    assertThat(resposta.dataReferencia()).isEqualTo(dataPlantao);
    assertThat(resposta.plantaoAtivo()).isTrue();
    assertThat(resposta.mensagem()).isEqualTo("Plantão ativo encontrado.");
    assertThat(resposta.plantoes()).hasSize(1);
    assertThat(resposta.plantoes().get(0).farmacia().nome()).isEqualTo("Farmácia Central");
  }

  @Test
  @DisplayName("Deve consultar plantão atual com distrito")
  void deveConsultarPlantaoAtualComDistrito() {
    LocalDateTime momento = LocalDateTime.of(2026, 5, 17, 10, 0);
    LocalDate dataPlantao = LocalDate.of(2026, 5, 17);
    String distrito = "Segundo Distrito";

    Farmacia farmacia = criarFarmacia("Farmácia Segundo Distrito", distrito);
    EscalaPlantao escala = criarEscala(dataPlantao, farmacia);

    when(escalaPlantaoRepository.findByDataPlantaoAndFarmaciaDistritoIgnoreCase(
            dataPlantao, distrito))
        .thenReturn(List.of(escala));

    ConsultaPlantaoAtualRespostaDTO resposta =
        plantaoService.consultarPlantaoAtual(distrito, momento);

    assertThat(resposta.dataReferencia()).isEqualTo(dataPlantao);
    assertThat(resposta.plantaoAtivo()).isTrue();
    assertThat(resposta.mensagem()).isEqualTo("Plantão ativo encontrado.");
    assertThat(resposta.plantoes()).hasSize(1);
    assertThat(resposta.plantoes().get(0).farmacia().distrito()).isEqualTo(distrito);
  }

  @Test
  @DisplayName("Deve retornar lista vazia quando não houver escala para o plantão atual")
  void deveRetornarListaVaziaQuandoNaoHouverEscalaParaPlantaoAtual() {
    LocalDateTime momento = LocalDateTime.of(2026, 5, 17, 10, 0);
    LocalDate dataPlantao = LocalDate.of(2026, 5, 17);

    when(escalaPlantaoRepository.findByDataPlantao(dataPlantao)).thenReturn(List.of());

    ConsultaPlantaoAtualRespostaDTO resposta = plantaoService.consultarPlantaoAtual(null, momento);

    assertThat(resposta.dataReferencia()).isEqualTo(dataPlantao);
    assertThat(resposta.plantaoAtivo()).isTrue();
    assertThat(resposta.mensagem())
        .isEqualTo("Não encontrei escala de plantão cadastrada para esta data no sistema.");
    assertThat(resposta.plantoes()).isEmpty();
  }

  // ===== FASE 8 - INÍCIO =====
  @Test
  @DisplayName("Deve consultar plantão por data sem distrito")
  void deveConsultarPlantaoPorDataSemDistrito() {
    LocalDate data = LocalDate.of(2026, 5, 17);

    Farmacia farmacia = criarFarmacia("Farmácia Real", "Primeiro Distrito");
    EscalaPlantao escala = criarEscala(data, farmacia);

    when(escalaPlantaoRepository.findByDataPlantao(data)).thenReturn(List.of(escala));

    ConsultaPlantaoAtualRespostaDTO resposta = plantaoService.consultarPlantaoPorData(data, null);

    assertThat(resposta.dataReferencia()).isEqualTo(data);
    assertThat(resposta.plantaoAtivo()).isTrue();
    assertThat(resposta.mensagem()).isEqualTo("Plantão encontrado para a data informada.");
    assertThat(resposta.plantoes()).hasSize(1);
    assertThat(resposta.plantoes().get(0).farmacia().nome()).isEqualTo("Farmácia Real");

    verify(escalaPlantaoRepository).findByDataPlantao(data);
  }

  @Test
  @DisplayName("Deve consultar plantão por data e distrito")
  void deveConsultarPlantaoPorDataEDistrito() {
    LocalDate data = LocalDate.of(2026, 5, 17);
    String distrito = "Segundo Distrito";

    Farmacia farmacia = criarFarmacia("Saúde Popular", distrito);
    EscalaPlantao escala = criarEscala(data, farmacia);

    when(escalaPlantaoRepository.findByDataPlantaoAndFarmaciaDistritoIgnoreCase(data, distrito))
        .thenReturn(List.of(escala));

    ConsultaPlantaoAtualRespostaDTO resposta =
        plantaoService.consultarPlantaoPorData(data, distrito);

    assertThat(resposta.dataReferencia()).isEqualTo(data);
    assertThat(resposta.plantaoAtivo()).isTrue();
    assertThat(resposta.mensagem()).isEqualTo("Plantão encontrado para a data informada.");
    assertThat(resposta.plantoes()).hasSize(1);
    assertThat(resposta.plantoes().get(0).farmacia().distrito()).isEqualTo(distrito);

    verify(escalaPlantaoRepository).findByDataPlantaoAndFarmaciaDistritoIgnoreCase(data, distrito);
  }

  @Test
  @DisplayName("Deve remover espaços do distrito na consulta por data")
  void deveRemoverEspacosDoDistritoNaConsultaPorData() {
    LocalDate data = LocalDate.of(2026, 5, 17);

    when(escalaPlantaoRepository.findByDataPlantaoAndFarmaciaDistritoIgnoreCase(
            data, "Primeiro Distrito"))
        .thenReturn(List.of());

    ConsultaPlantaoAtualRespostaDTO resposta =
        plantaoService.consultarPlantaoPorData(data, "  Primeiro Distrito  ");

    assertThat(resposta.dataReferencia()).isEqualTo(data);
    assertThat(resposta.plantoes()).isEmpty();

    verify(escalaPlantaoRepository)
        .findByDataPlantaoAndFarmaciaDistritoIgnoreCase(data, "Primeiro Distrito");
  }

  @Test
  @DisplayName("Deve retornar lista vazia quando não houver escala na data informada")
  void deveRetornarListaVaziaQuandoNaoHouverEscalaNaDataInformada() {
    LocalDate data = LocalDate.of(2026, 5, 30);

    when(escalaPlantaoRepository.findByDataPlantao(data)).thenReturn(List.of());

    ConsultaPlantaoAtualRespostaDTO resposta = plantaoService.consultarPlantaoPorData(data, null);

    assertThat(resposta.dataReferencia()).isEqualTo(data);
    assertThat(resposta.plantaoAtivo()).isTrue();
    assertThat(resposta.mensagem())
        .isEqualTo("Não encontrei escala de plantão cadastrada para esta data no sistema.");
    assertThat(resposta.plantoes()).isEmpty();
  }

  @Test
  @DisplayName("Deve lançar exceção quando data da consulta por data for nula")
  void deveLancarExcecaoQuandoDataDaConsultaPorDataForNula() {
    assertThatThrownBy(() -> plantaoService.consultarPlantaoPorData(null, null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("A data da consulta não pode ser nula.");
  }

  // ===== FASE 8 - FIM =====

  private Farmacia criarFarmacia(String nome, String distrito) {
    return new Farmacia(nome, "Rua Teste, 123", "Centro", distrito, "(69) 99999-9999");
  }

  private EscalaPlantao criarEscala(LocalDate dataPlantao, Farmacia farmacia) {
    return new EscalaPlantao(farmacia, dataPlantao, LocalTime.of(7, 0), LocalTime.of(7, 0), null);
  }
}
