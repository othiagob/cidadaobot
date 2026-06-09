package br.com.othiagob.cidadaobot.plantao.dto;

import br.com.othiagob.cidadaobot.farmacia.Farmacia;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Schema(description = "Dados públicos da farmácia.")
public record FarmaciaRespostaDTO(
    @Schema(description = "Nome da farmácia.", example = "FARMACIA REAL") String nome,
    @Schema(
            description = "Endereço da farmácia.",
            example = "RUA DOS MINEIROS, 298 - PRÓXIMO À RODOVIÁRIA")
        String endereco,
    @Schema(description = "Bairro onde a farmácia está localizada.", example = "CENTRO")
        String bairro,
    @Schema(
            description = "Distrito de Ji-Paraná ao qual a farmácia pertence.",
            example = "Primeiro Distrito")
        String distrito,
    @Schema(description = "Telefone de contato da farmácia.", example = "3422-3491")
        String telefone)
    implements Serializable {

  public static FarmaciaRespostaDTO from(Farmacia farmacia) {
    return new FarmaciaRespostaDTO(
        farmacia.getNome(),
        farmacia.getEndereco(),
        farmacia.getBairro(),
        farmacia.getDistrito(),
        farmacia.getTelefone());
  }
}
