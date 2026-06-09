package br.com.othiagob.cidadaobot.plantao.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Schema(description = "Resposta da consulta de plantão.")
public record ConsultaPlantaoAtualRespostaDTO(
    @Schema(
            description = "Data usada como referência para localizar a escala de plantão.",
            example = "2026-05-19")
        LocalDate dataReferencia,
    @Schema(
            description = "Indica se existe plantão ativo para a consulta realizada.",
            example = "true")
        boolean plantaoAtivo,
    @Schema(
            description = "Mensagem resumida sobre o resultado da consulta.",
            example = "Plantão ativo encontrado.")
        String mensagem,
    @Schema(description = "Lista de farmácias encontradas para o plantão consultado.")
        List<PlantaoRespostaDTO> plantoes)
    implements Serializable {}
