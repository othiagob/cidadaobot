package br.com.othiagob.cidadaobot.farmacia;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmaciaRepository extends JpaRepository<Farmacia, Long> {

  List<Farmacia> findByDistrito(String distrito);

  List<Farmacia> findByBairro(String bairro);
}
