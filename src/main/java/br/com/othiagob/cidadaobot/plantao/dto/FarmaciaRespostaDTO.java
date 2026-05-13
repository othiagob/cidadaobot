package br.com.othiagob.cidadaobot.plantao.dto;

import br.com.othiagob.cidadaobot.farmacia.Farmacia;

public record FarmaciaRespostaDTO(
    String nome, String endereco, String bairro, String distrito, String telefone) {
  public static FarmaciaRespostaDTO from(Farmacia farmacia) {
    return new FarmaciaRespostaDTO(
        farmacia.getNome(),
        farmacia.getEndereco(),
        farmacia.getBairro(),
        farmacia.getDistrito(),
        farmacia.getTelefone());
  }
}
