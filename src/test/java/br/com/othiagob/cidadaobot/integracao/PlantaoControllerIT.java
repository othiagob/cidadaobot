package br.com.othiagob.cidadaobot.integracao;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

class PlantaoControllerIT extends BaseIntegrationTest {

  @Autowired private TestRestTemplate restTemplate;

  @Autowired private ObjectMapper objectMapper;

  @Test
  @DisplayName("Deve consultar plantão por data usando fluxo completo da aplicação")
  void deveConsultarPlantaoPorDataUsandoFluxoCompletoDaAplicacao() throws Exception {
    ResponseEntity<String> resposta =
        restTemplate.getForEntity("/api/plantoes?data=2026-05-20", String.class);

    assertThat(resposta.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(resposta.getBody()).isNotBlank();

    JsonNode json = objectMapper.readTree(resposta.getBody());

    assertThat(json.path("sucesso").asBoolean()).isTrue();
    assertThat(json.path("mensagem").asText()).isNotBlank();
    assertThat(json.path("dados").path("dataReferencia").asText()).isEqualTo("2026-05-20");
    assertThat(json.path("dados").path("plantaoAtivo").asBoolean()).isTrue();
    assertThat(json.path("dados").path("plantoes").isArray()).isTrue();
    assertThat(json.path("timestamp").asText()).isNotBlank();
  }

  @Test
  @DisplayName("Deve consultar plantão por data filtrando por distrito")
  void deveConsultarPlantaoPorDataFiltrandoPorDistrito() throws Exception {
    ResponseEntity<String> resposta =
        restTemplate.getForEntity(
            "/api/plantoes?data=2026-05-20&distrito=Primeiro%20Distrito", String.class);

    assertThat(resposta.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(resposta.getBody()).isNotBlank();

    JsonNode json = objectMapper.readTree(resposta.getBody());

    assertThat(json.path("sucesso").asBoolean()).isTrue();
    assertThat(json.path("mensagem").asText()).isNotBlank();
    assertThat(json.path("dados").path("dataReferencia").asText()).isEqualTo("2026-05-20");
    assertThat(json.path("dados").path("plantaoAtivo").asBoolean()).isTrue();
    assertThat(json.path("dados").path("plantoes").isArray()).isTrue();
    assertThat(json.path("timestamp").asText()).isNotBlank();

    JsonNode plantoes = json.path("dados").path("plantoes");

    for (JsonNode plantao : plantoes) {
      assertThat(plantao.path("farmacia").path("distrito").asText())
          .isEqualToIgnoringCase("Primeiro Distrito");
    }
  }

  @Test
  @DisplayName("Deve consultar plantão atual usando fluxo completo da aplicação")
  void deveConsultarPlantaoAtualUsandoFluxoCompletoDaAplicacao() throws Exception {
    ResponseEntity<String> resposta =
        restTemplate.getForEntity("/api/plantoes/atual", String.class);

    assertThat(resposta.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(resposta.getBody()).isNotBlank();

    JsonNode json = objectMapper.readTree(resposta.getBody());

    assertThat(json.path("sucesso").asBoolean()).isTrue();
    assertThat(json.path("mensagem").asText()).isNotBlank();
    assertThat(json.path("dados").path("dataReferencia").asText()).isNotBlank();
    assertThat(json.path("dados").path("plantaoAtivo").asBoolean()).isTrue();
    assertThat(json.path("dados").path("mensagem").asText()).isNotBlank();
    assertThat(json.path("dados").path("plantoes").isArray()).isTrue();
    assertThat(json.path("timestamp").asText()).isNotBlank();
  }

  @Test
  @DisplayName("Deve consultar plantão atual filtrando por distrito")
  void deveConsultarPlantaoAtualFiltrandoPorDistrito() throws Exception {
    ResponseEntity<String> resposta =
        restTemplate.getForEntity("/api/plantoes/atual?distrito=Primeiro%20Distrito", String.class);

    assertThat(resposta.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(resposta.getBody()).isNotBlank();

    JsonNode json = objectMapper.readTree(resposta.getBody());

    assertThat(json.path("sucesso").asBoolean()).isTrue();
    assertThat(json.path("mensagem").asText()).isNotBlank();
    assertThat(json.path("dados").path("dataReferencia").asText()).isNotBlank();
    assertThat(json.path("dados").path("plantaoAtivo").asBoolean()).isTrue();
    assertThat(json.path("dados").path("plantoes").isArray()).isTrue();
    assertThat(json.path("timestamp").asText()).isNotBlank();

    JsonNode plantoes = json.path("dados").path("plantoes");

    for (JsonNode plantao : plantoes) {
      assertThat(plantao.path("farmacia").path("distrito").asText())
          .isEqualToIgnoringCase("Primeiro Distrito");
    }
  }

  @Test
  @DisplayName("Deve retornar lista vazia quando distrito não existir")
  void deveRetornarListaVaziaQuandoDistritoNaoExistir() throws Exception {
    ResponseEntity<String> resposta =
        restTemplate.getForEntity(
            "/api/plantoes?data=2026-05-20&distrito=Distrito%20Inexistente", String.class);

    assertThat(resposta.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(resposta.getBody()).isNotBlank();

    JsonNode json = objectMapper.readTree(resposta.getBody());

    assertThat(json.path("sucesso").asBoolean()).isTrue();
    assertThat(json.path("mensagem").asText()).isNotBlank();
    assertThat(json.path("dados").path("dataReferencia").asText()).isEqualTo("2026-05-20");
    assertThat(json.path("dados").path("plantaoAtivo").asBoolean()).isTrue();
    assertThat(json.path("dados").path("plantoes").isArray()).isTrue();
    assertThat(json.path("dados").path("plantoes").isEmpty()).isTrue();
    assertThat(json.path("timestamp").asText()).isNotBlank();
  }

  @Test
  @DisplayName("Deve retornar erro padronizado quando a data for inválida")
  void deveRetornarErroPadronizadoQuandoDataForInvalida() throws Exception {
    ResponseEntity<String> resposta =
        restTemplate.getForEntity("/api/plantoes?data=20-05-2026", String.class);

    assertThat(resposta.getStatusCode().is4xxClientError()).isTrue();
    assertThat(resposta.getBody()).isNotBlank();

    JsonNode json = objectMapper.readTree(resposta.getBody());

    assertThat(json.path("sucesso").asBoolean()).isFalse();
    assertThat(json.path("mensagem").asText())
        .isEqualTo("Data inválida. Formato esperado: AAAA-MM-DD.");
    assertThat(json.path("dados").isNull()).isTrue();
    assertThat(json.path("timestamp").asText()).isNotBlank();
  }

  @Test
  @DisplayName("Deve retornar erro padronizado quando parâmetro data estiver ausente")
  void deveRetornarErroPadronizadoQuandoParametroDataEstiverAusente() throws Exception {
    ResponseEntity<String> resposta = restTemplate.getForEntity("/api/plantoes", String.class);

    assertThat(resposta.getStatusCode().is4xxClientError()).isTrue();
    assertThat(resposta.getBody()).isNotBlank();

    JsonNode json = objectMapper.readTree(resposta.getBody());

    assertThat(json.path("sucesso").asBoolean()).isFalse();
    assertThat(json.path("mensagem").asText()).isNotBlank();
    assertThat(json.path("dados").isNull()).isTrue();
    assertThat(json.path("timestamp").asText()).isNotBlank();
  }
}
