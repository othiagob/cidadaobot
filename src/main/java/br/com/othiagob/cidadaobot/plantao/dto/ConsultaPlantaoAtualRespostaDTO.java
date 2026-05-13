package br.com.othiagob.cidadaobot.plantao.dto;

import java.time.LocalDate;
import java.util.List;

public record ConsultaPlantaoAtualRespostaDTO(
    LocalDate dataReferencia,
    boolean plantaoAtivo,
    String mensagem,
    List<PlantaoRespostaDTO> plantoes) {}
