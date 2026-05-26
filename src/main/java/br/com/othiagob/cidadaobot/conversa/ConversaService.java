package br.com.othiagob.cidadaobot.conversa;

import br.com.othiagob.cidadaobot.conversa.dto.ConversaRegistroRequestDTO;
import br.com.othiagob.cidadaobot.conversa.dto.ConversaRespostaDTO;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConversaService {

  private static final int LIMITE_PADRAO_HISTORICO = 10;
  private static final int LIMITE_MAXIMO_HISTORICO = 100;

  private final ConversaRepository conversaRepository;

  public ConversaService(ConversaRepository conversaRepository) {
    this.conversaRepository = conversaRepository;
  }

  @Transactional
  public ConversaRespostaDTO registrarInteracao(ConversaRegistroRequestDTO requestDTO) {
    Conversa conversa =
        new Conversa(
            requestDTO.telefoneUsuario(),
            requestDTO.mensagemUsuario(),
            requestDTO.respostaEnviada(),
            requestDTO.intencaoDetectada(),
            requestDTO.distritoDetectado(),
            requestDTO.dataReferencia(),
            requestDTO.origem());

    Conversa conversaSalva = conversaRepository.save(conversa);

    return converterParaRespostaDTO(conversaSalva);
  }

  @Transactional(readOnly = true)
  public List<ConversaRespostaDTO> consultarHistoricoPorTelefone(
      String telefoneUsuario, Integer limite) {
    int limiteValidado = validarLimite(limite);

    return conversaRepository
        .findByTelefoneUsuarioOrderByCriadaEmDesc(
            telefoneUsuario, PageRequest.of(0, limiteValidado))
        .stream()
        .map(this::converterParaRespostaDTO)
        .toList();
  }

  private int validarLimite(Integer limite) {
    if (limite == null) {
      return LIMITE_PADRAO_HISTORICO;
    }

    if (limite < 1) {
      throw new IllegalArgumentException("Limite deve ser maior que zero.");
    }

    if (limite > LIMITE_MAXIMO_HISTORICO) {
      throw new IllegalArgumentException("Limite máximo permitido é 100.");
    }

    return limite;
  }

  private ConversaRespostaDTO converterParaRespostaDTO(Conversa conversa) {
    return new ConversaRespostaDTO(
        conversa.getId(),
        conversa.getTelefoneUsuario(),
        conversa.getMensagemUsuario(),
        conversa.getRespostaEnviada(),
        conversa.getIntencaoDetectada(),
        conversa.getDistritoDetectado(),
        conversa.getDataReferencia(),
        conversa.getOrigem(),
        conversa.getCriadaEm());
  }
}
