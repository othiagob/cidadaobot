package br.com.othiagob.cidadaobot.plantao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.othiagob.cidadaobot.farmacia.Farmacia;
import br.com.othiagob.cidadaobot.plantao.dto.ConsultaPlantaoAtualRespostaDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PlantaoServiceTest {

  @Mock private EscalaPlantaoRepository escalaPlantaoRepository;

  @InjectMocks private PlantaoService plantaoService;

  @Test
  @DisplayName("18:59 não deve ter plantão ativo")
  void deveRetornarVazioQuandoHorarioForDezoitoECinquentaENove() {
    LocalDateTime momento = LocalDateTime.of(2026, 5, 10, 18, 59);

    Optional<LocalDate> resultado = plantaoService.determinarDataPlantaoAtivo(momento);

    assertThat(resultado).isEmpty();
  }

  @Test
  @DisplayName("19:00 deve retornar plantão da data atual")
  void deveRetornarDataAtualQuandoHorarioForDezenoveHoras() {
    LocalDateTime momento = LocalDateTime.of(2026, 5, 10, 19, 0);

    Optional<LocalDate> resultado = plantaoService.determinarDataPlantaoAtivo(momento);

    assertThat(resultado).contains(LocalDate.of(2026, 5, 10));
  }

  @Test
  @DisplayName("23:59 deve retornar plantão da data atual")
  void deveRetornarDataAtualQuandoHorarioForVinteETresECinquentaENove() {
    LocalDateTime momento = LocalDateTime.of(2026, 5, 10, 23, 59);

    Optional<LocalDate> resultado = plantaoService.determinarDataPlantaoAtivo(momento);

    assertThat(resultado).contains(LocalDate.of(2026, 5, 10));
  }

  @Test
  @DisplayName("00:00 deve retornar plantão do dia anterior")
  void deveRetornarDiaAnteriorQuandoHorarioForMeiaNoite() {
    LocalDateTime momento = LocalDateTime.of(2026, 5, 11, 0, 0);

    Optional<LocalDate> resultado = plantaoService.determinarDataPlantaoAtivo(momento);

    assertThat(resultado).contains(LocalDate.of(2026, 5, 10));
  }

  @Test
  @DisplayName("06:59 deve retornar plantão do dia anterior")
  void deveRetornarDiaAnteriorQuandoHorarioForSeisECinquentaENove() {
    LocalDateTime momento = LocalDateTime.of(2026, 5, 11, 6, 59);

    Optional<LocalDate> resultado = plantaoService.determinarDataPlantaoAtivo(momento);

    assertThat(resultado).contains(LocalDate.of(2026, 5, 10));
  }

  @Test
  @DisplayName("07:00 não deve ter plantão ativo")
  void deveRetornarVazioQuandoHorarioForSeteHoras() {
    LocalDateTime momento = LocalDateTime.of(2026, 5, 11, 7, 0);

    Optional<LocalDate> resultado = plantaoService.determinarDataPlantaoAtivo(momento);

    assertThat(resultado).isEmpty();
  }

  @Test
  @DisplayName("Deve lançar erro quando momento for nulo")
  void deveLancarErroQuandoMomentoForNulo() {
    assertThatThrownBy(() -> plantaoService.determinarDataPlantaoAtivo(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("O momento da consulta não pode ser nulo.");
  }

  @Test
  @DisplayName("Deve buscar plantões ativos pela data calculada")
  void deveBuscarPlantoesAtivosPelaDataCalculada() {
    LocalDateTime momento = LocalDateTime.of(2026, 5, 10, 19, 0);
    LocalDate dataPlantao = LocalDate.of(2026, 5, 10);

    EscalaPlantao escala = criarEscalaPlantao(dataPlantao, "Primeiro Distrito");

    when(escalaPlantaoRepository.findByDataPlantao(dataPlantao)).thenReturn(List.of(escala));

    List<EscalaPlantao> resultado = plantaoService.buscarPlantoesAtivos(momento);

    assertThat(resultado).hasSize(1);
    assertThat(resultado.get(0).getDataPlantao()).isEqualTo(dataPlantao);

    verify(escalaPlantaoRepository).findByDataPlantao(dataPlantao);
  }

  @Test
  @DisplayName("Não deve consultar repository quando estiver fora do horário de plantão")
  void naoDeveConsultarRepositoryQuandoEstiverForaDoHorarioDePlantao() {
    LocalDateTime momento = LocalDateTime.of(2026, 5, 10, 12, 0);

    List<EscalaPlantao> resultado = plantaoService.buscarPlantoesAtivos(momento);

    assertThat(resultado).isEmpty();

    verify(escalaPlantaoRepository, never()).findByDataPlantao(any());
  }

  @Test
  @DisplayName("Deve buscar plantões ativos por distrito")
  void deveBuscarPlantoesAtivosPorDistrito() {
    LocalDateTime momento = LocalDateTime.of(2026, 5, 10, 22, 0);
    LocalDate dataPlantao = LocalDate.of(2026, 5, 10);
    String distrito = "Primeiro Distrito";

    EscalaPlantao escala = criarEscalaPlantao(dataPlantao, distrito);

    when(escalaPlantaoRepository.findByDataPlantaoAndFarmaciaDistritoIgnoreCase(
            dataPlantao, distrito))
        .thenReturn(List.of(escala));

    List<EscalaPlantao> resultado =
        plantaoService.buscarPlantoesAtivosPorDistrito(momento, distrito);

    assertThat(resultado).hasSize(1);
    assertThat(resultado.get(0).getFarmacia().getDistrito()).isEqualTo(distrito);

    verify(escalaPlantaoRepository)
        .findByDataPlantaoAndFarmaciaDistritoIgnoreCase(dataPlantao, distrito);
  }

  @Test
  @DisplayName("Deve remover espaços extras do distrito antes da busca")
  void deveRemoverEspacosExtrasDoDistritoAntesDaBusca() {
    LocalDateTime momento = LocalDateTime.of(2026, 5, 10, 22, 0);
    LocalDate dataPlantao = LocalDate.of(2026, 5, 10);

    plantaoService.buscarPlantoesAtivosPorDistrito(momento, "  Primeiro Distrito  ");

    verify(escalaPlantaoRepository)
        .findByDataPlantaoAndFarmaciaDistritoIgnoreCase(dataPlantao, "Primeiro Distrito");
  }

  @Test
  @DisplayName("Deve lançar erro quando distrito for nulo")
  void deveLancarErroQuandoDistritoForNulo() {
    LocalDateTime momento = LocalDateTime.of(2026, 5, 10, 22, 0);

    assertThatThrownBy(() -> plantaoService.buscarPlantoesAtivosPorDistrito(momento, null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("O distrito não pode ser nulo ou vazio.");
  }

  @Test
  @DisplayName("Deve lançar erro quando distrito for vazio")
  void deveLancarErroQuandoDistritoForVazio() {
    LocalDateTime momento = LocalDateTime.of(2026, 5, 10, 22, 0);

    assertThatThrownBy(() -> plantaoService.buscarPlantoesAtivosPorDistrito(momento, "   "))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("O distrito não pode ser nulo ou vazio.");
  }

  @Test
  @DisplayName("Deve consultar plantão atual fora do horário e retornar plantão inativo")
  void deveConsultarPlantaoAtualForaDoHorarioERetornarPlantaoInativo() {
    LocalDateTime momento = LocalDateTime.of(2026, 5, 10, 12, 0);

    ConsultaPlantaoAtualRespostaDTO resposta = plantaoService.consultarPlantaoAtual(null, momento);

    assertThat(resposta.dataReferencia()).isNull();
    assertThat(resposta.plantaoAtivo()).isFalse();
    assertThat(resposta.mensagem())
        .isEqualTo("Não há plantão ativo neste horário. O plantão funciona das 19:00 às 07:00.");
    assertThat(resposta.plantoes()).isEmpty();

    verify(escalaPlantaoRepository, never()).findByDataPlantao(any());
  }

  @Test
  @DisplayName("Deve consultar plantão atual sem distrito quando existir escala cadastrada")
  void deveConsultarPlantaoAtualSemDistritoQuandoExistirEscalaCadastrada() {
    LocalDateTime momento = LocalDateTime.of(2026, 5, 10, 22, 0);
    LocalDate dataPlantao = LocalDate.of(2026, 5, 10);

    EscalaPlantao escala = criarEscalaPlantao(dataPlantao, "Primeiro Distrito");

    when(escalaPlantaoRepository.findByDataPlantao(dataPlantao)).thenReturn(List.of(escala));

    ConsultaPlantaoAtualRespostaDTO resposta = plantaoService.consultarPlantaoAtual(null, momento);

    assertThat(resposta.dataReferencia()).isEqualTo(dataPlantao);
    assertThat(resposta.plantaoAtivo()).isTrue();
    assertThat(resposta.mensagem()).isEqualTo("Plantão ativo encontrado.");
    assertThat(resposta.plantoes()).hasSize(1);
    assertThat(resposta.plantoes().get(0).farmacia().nome()).isEqualTo("Farmácia Teste");
    assertThat(resposta.plantoes().get(0).farmacia().distrito()).isEqualTo("Primeiro Distrito");

    verify(escalaPlantaoRepository).findByDataPlantao(dataPlantao);
  }

  @Test
  @DisplayName("Deve consultar plantão atual por distrito quando distrito for informado")
  void deveConsultarPlantaoAtualPorDistritoQuandoDistritoForInformado() {
    LocalDateTime momento = LocalDateTime.of(2026, 5, 10, 22, 0);
    LocalDate dataPlantao = LocalDate.of(2026, 5, 10);
    String distrito = "Segundo Distrito";

    EscalaPlantao escala = criarEscalaPlantao(dataPlantao, distrito);

    when(escalaPlantaoRepository.findByDataPlantaoAndFarmaciaDistritoIgnoreCase(
            dataPlantao, distrito))
        .thenReturn(List.of(escala));

    ConsultaPlantaoAtualRespostaDTO resposta =
        plantaoService.consultarPlantaoAtual(distrito, momento);

    assertThat(resposta.dataReferencia()).isEqualTo(dataPlantao);
    assertThat(resposta.plantaoAtivo()).isTrue();
    assertThat(resposta.mensagem()).isEqualTo("Plantão ativo encontrado.");
    assertThat(resposta.plantoes()).hasSize(1);
    assertThat(resposta.plantoes().get(0).farmacia().distrito()).isEqualTo("Segundo Distrito");

    verify(escalaPlantaoRepository)
        .findByDataPlantaoAndFarmaciaDistritoIgnoreCase(dataPlantao, distrito);
  }

  @Test
  @DisplayName(
      "Deve consultar plantão atual e retornar mensagem quando não houver escala cadastrada")
  void deveConsultarPlantaoAtualERetornarMensagemQuandoNaoHouverEscalaCadastrada() {
    LocalDateTime momento = LocalDateTime.of(2026, 5, 10, 22, 0);
    LocalDate dataPlantao = LocalDate.of(2026, 5, 10);

    when(escalaPlantaoRepository.findByDataPlantao(dataPlantao)).thenReturn(List.of());

    ConsultaPlantaoAtualRespostaDTO resposta = plantaoService.consultarPlantaoAtual(null, momento);

    assertThat(resposta.dataReferencia()).isEqualTo(dataPlantao);
    assertThat(resposta.plantaoAtivo()).isTrue();
    assertThat(resposta.mensagem())
        .isEqualTo("Não encontrei escala de plantão cadastrada para esta data no sistema.");
    assertThat(resposta.plantoes()).isEmpty();

    verify(escalaPlantaoRepository).findByDataPlantao(dataPlantao);
  }

  private EscalaPlantao criarEscalaPlantao(LocalDate dataPlantao, String distrito) {
    Farmacia farmacia =
        new Farmacia("Farmácia Teste", "Rua Teste, 123", "Centro", distrito, "(69) 99999-9999");

    return new EscalaPlantao(
        farmacia, dataPlantao, LocalTime.of(19, 0), LocalTime.of(7, 0), "Plantão de teste");
  }
}
