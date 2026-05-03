package br.com.othiagob.cidadaobot.farmacia;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest(showSql = false)
@ActiveProfiles("test")
class FarmaciaRepositoryTest {

  @Autowired private FarmaciaRepository farmaciaRepository;

  @Test
  @DisplayName("Deve buscar farmacias por distrito")
  void deveBuscarFarmaciasPorDistrito() {
    Farmacia farmaciaPrimeiroDistrito =
        new Farmacia("Farmacia Centro", "Rua A, 123", "Centro", "Primeiro Distrito", "69999990000");
    Farmacia farmaciaSegundoDistrito =
        new Farmacia("Farmacia Sul", "Rua B, 456", "Sul", "Segundo Distrito", "69999990001");
    farmaciaRepository.saveAll(List.of(farmaciaPrimeiroDistrito, farmaciaSegundoDistrito));

    List<Farmacia> farmacias = farmaciaRepository.findByDistrito("Primeiro Distrito");

    assertThat(farmacias)
        .isNotEmpty()
        .allMatch(farmacia -> farmacia.getDistrito().equals("Primeiro Distrito"));
  }

  @Test
  @DisplayName("Deve buscar farmacias por bairro")
  void deveBuscarFarmaciasPorBairro() {
    Farmacia farmaciaCentro =
        new Farmacia("Farmacia Centro", "Rua A, 123", "Centro", "Primeiro Distrito", "69999990000");
    Farmacia farmaciaNorte =
        new Farmacia("Farmacia Norte", "Rua C, 789", "Norte", "Primeiro Distrito", "69999990002");
    farmaciaRepository.saveAll(List.of(farmaciaCentro, farmaciaNorte));

    List<Farmacia> farmacias = farmaciaRepository.findByBairro("Centro");

    assertThat(farmacias)
        .isNotEmpty()
        .allMatch(farmacia -> farmacia.getBairro().equals("Centro"));
  }
}
