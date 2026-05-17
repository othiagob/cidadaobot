package br.com.othiagob.cidadaobot.plantao;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.othiagob.cidadaobot.farmacia.Farmacia;
import br.com.othiagob.cidadaobot.farmacia.FarmaciaRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class EscalaPlantaoRepositoryTest {

  @Autowired private EscalaPlantaoRepository escalaPlantaoRepository;

  @Autowired private FarmaciaRepository farmaciaRepository;

  @Test
  @DisplayName("Deve salvar escala de plantão relacionada a uma farmácia")
  void deveSalvarEscalaPlantaoComFarmacia() {
    Farmacia farmacia =
        criarFarmacia(
            "Farmácia Central",
            "Av. Brasil, 100",
            "Centro",
            "Primeiro Distrito",
            "(69) 99999-9999");

    EscalaPlantao escalaPlantao =
        criarEscalaPlantao(farmacia, LocalDate.of(2030, 1, 10), "Plantão de teste");

    EscalaPlantao escalaSalva = escalaPlantaoRepository.save(escalaPlantao);

    assertThat(escalaSalva.getId()).isNotNull();
    assertThat(escalaSalva.getFarmacia()).isNotNull();
    assertThat(escalaSalva.getFarmacia().getNome()).isEqualTo("Farmácia Central");
    assertThat(escalaSalva.getDataPlantao()).isEqualTo(LocalDate.of(2030, 1, 10));
    assertThat(escalaSalva.getIniciaAs()).isEqualTo(LocalTime.of(7, 0));
    assertThat(escalaSalva.getTerminaAs()).isEqualTo(LocalTime.of(7, 0));
    assertThat(escalaSalva.getObservacoes()).isEqualTo("Plantão de teste");
  }

  @Test
  @DisplayName("Deve buscar escalas por data do plantão")
  void deveBuscarEscalasPorDataPlantao() {
    Farmacia farmacia =
        criarFarmacia(
            "Farmácia Central",
            "Av. Brasil, 100",
            "Centro",
            "Primeiro Distrito",
            "(69) 99999-9999");

    EscalaPlantao escalaPlantao = criarEscalaPlantao(farmacia, LocalDate.of(2030, 1, 10), null);

    escalaPlantaoRepository.save(escalaPlantao);

    List<EscalaPlantao> resultado =
        escalaPlantaoRepository.findByDataPlantao(LocalDate.of(2030, 1, 10));

    assertThat(resultado).hasSize(1);
    assertThat(resultado.get(0).getFarmacia().getNome()).isEqualTo("Farmácia Central");
    assertThat(resultado.get(0).getDataPlantao()).isEqualTo(LocalDate.of(2030, 1, 10));
  }

  @Test
  @DisplayName("Deve buscar escalas por data e distrito da farmácia")
  void deveBuscarEscalasPorDataEDistrito() {
    Farmacia farmaciaPrimeiroDistrito =
        criarFarmacia(
            "Farmácia Central",
            "Av. Brasil, 100",
            "Centro",
            "Primeiro Distrito",
            "(69) 99999-9999");

    Farmacia farmaciaSegundoDistrito =
        criarFarmacia(
            "Farmácia Norte",
            "Rua Amazonas, 200",
            "Nova Brasília",
            "Segundo Distrito",
            "(69) 98888-8888");

    EscalaPlantao escalaPrimeiroDistrito =
        criarEscalaPlantao(farmaciaPrimeiroDistrito, LocalDate.of(2030, 1, 10), null);

    EscalaPlantao escalaSegundoDistrito =
        criarEscalaPlantao(farmaciaSegundoDistrito, LocalDate.of(2030, 1, 10), null);

    escalaPlantaoRepository.save(escalaPrimeiroDistrito);
    escalaPlantaoRepository.save(escalaSegundoDistrito);

    List<EscalaPlantao> resultado =
        escalaPlantaoRepository.findByDataPlantaoAndFarmaciaDistritoIgnoreCase(
            LocalDate.of(2030, 1, 10), "Primeiro Distrito");

    assertThat(resultado).hasSize(1);
    assertThat(resultado.get(0).getFarmacia().getNome()).isEqualTo("Farmácia Central");
    assertThat(resultado.get(0).getFarmacia().getDistrito()).isEqualTo("Primeiro Distrito");
  }

  @Test
  @DisplayName("Não deve retornar escala quando não existir plantão para a data informada")
  void naoDeveRetornarEscalaQuandoDataNaoExistir() {
    Farmacia farmacia =
        criarFarmacia(
            "Farmácia Central",
            "Av. Brasil, 100",
            "Centro",
            "Primeiro Distrito",
            "(69) 99999-9999");

    EscalaPlantao escalaPlantao = criarEscalaPlantao(farmacia, LocalDate.of(2030, 1, 10), null);

    escalaPlantaoRepository.save(escalaPlantao);

    List<EscalaPlantao> resultado =
        escalaPlantaoRepository.findByDataPlantao(LocalDate.of(2030, 1, 11));

    assertThat(resultado).isEmpty();
  }

  @Test
  @DisplayName("Não deve retornar escala quando distrito não corresponder")
  void naoDeveRetornarEscalaQuandoDistritoNaoCorresponder() {
    Farmacia farmacia =
        criarFarmacia(
            "Farmácia Central",
            "Av. Brasil, 100",
            "Centro",
            "Primeiro Distrito",
            "(69) 99999-9999");

    EscalaPlantao escalaPlantao = criarEscalaPlantao(farmacia, LocalDate.of(2030, 1, 10), null);

    escalaPlantaoRepository.save(escalaPlantao);

    List<EscalaPlantao> resultado =
        escalaPlantaoRepository.findByDataPlantaoAndFarmaciaDistritoIgnoreCase(
            LocalDate.of(2030, 1, 10), "Segundo Distrito");

    assertThat(resultado).isEmpty();
  }

  private Farmacia criarFarmacia(
      String nome, String endereco, String bairro, String distrito, String telefone) {
    Farmacia farmacia = new Farmacia(nome, endereco, bairro, distrito, telefone);
    return farmaciaRepository.save(farmacia);
  }

  private EscalaPlantao criarEscalaPlantao(
      Farmacia farmacia, LocalDate dataPlantao, String observacoes) {
    return new EscalaPlantao(
        farmacia, dataPlantao, LocalTime.of(7, 0), LocalTime.of(7, 0), observacoes);
  }
}
