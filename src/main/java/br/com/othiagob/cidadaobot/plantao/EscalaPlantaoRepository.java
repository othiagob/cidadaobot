package br.com.othiagob.cidadaobot.plantao;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EscalaPlantaoRepository extends JpaRepository<EscalaPlantao, Long> {
  List<EscalaPlantao> findByDataPlantao(LocalDate dataPlantao);

  List<EscalaPlantao> findByDataPlantaoAndFarmaciaDistritoIgnoreCase(
      LocalDate dataPlantao, String distrito);
}
