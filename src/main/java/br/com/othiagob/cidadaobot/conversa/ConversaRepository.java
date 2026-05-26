package br.com.othiagob.cidadaobot.conversa;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversaRepository extends JpaRepository<Conversa, Long> {
  List<Conversa> findByTelefoneUsuarioOrderByCriadaEmDesc(String telefoneUsuario);

  List<Conversa> findByTelefoneUsuarioOrderByCriadaEmDesc(
      String telefoneUsuario, Pageable pageable);
}
