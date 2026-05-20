package br.com.othiagob.cidadaobot.plantao.dto;

import br.com.othiagob.cidadaobot.farmacia.Farmacia;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "FarmaciaResposta",
    description = "Dados públicos da farmácia retornados nas consultas de plantão.")
public record FarmaciaRespostaDTO(
    @Schema(description = "Nome da farmácia.", example = "Farmácia Real") String nome,
    @Schema(description = "Endereço da farmácia.", example = "Rua dos Mineiros, 298")
        String endereco,
    @Schema(description = "Bairro onde a farmácia está localizada.", example = "Centro")
        String bairro,
    @Schema(
            description = "Distrito de Ji-Paraná ao qual a farmácia pertence.",
            example = "Primeiro Distrito")
        String distrito,
    @Schema(description = "Telefone de contato da farmácia.", example = "3422-3491")
        String telefone) {
  public static FarmaciaRespostaDTO from(Farmacia farmacia) {
    return new FarmaciaRespostaDTO(
        farmacia.getNome(),
        farmacia.getEndereco(),
        farmacia.getBairro(),
        farmacia.getDistrito(),
        farmacia.getTelefone());
  }
}
