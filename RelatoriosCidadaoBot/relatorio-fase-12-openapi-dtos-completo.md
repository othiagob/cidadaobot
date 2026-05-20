# Relatório — Fase 12 OpenAPI DTOs

## 1. Visão geral

Este relatório descreve as alterações realizadas na documentação OpenAPI/Swagger da API CidadãoBot, especificamente no módulo de Farmácias de Plantão.

O objetivo da fase foi melhorar a documentação exibida no Swagger UI para os endpoints REST existentes e para os DTOs usados nas respostas da API, sem alterar regra de negócio, sem criar endpoints novos e sem expor entidades JPA.

## 2. Escopo da alteração

As mudanças ficaram restritas à documentação OpenAPI/Swagger por meio de annotations do pacote:

```java
io.swagger.v3.oas.annotations
```

Não houve alteração em:

- Services
- Repositories
- Entities
- Migrations Flyway
- Regras de negócio
- Contratos REST existentes
- Comportamento das respostas da API

## 3. Arquivos alterados

| Arquivo | Finalidade |
| --- | --- |
| `src/main/java/br/com/othiagob/cidadaobot/plantao/PlantaoController.java` | Documentação Swagger dos endpoints de plantão |
| `src/main/java/br/com/othiagob/cidadaobot/plantao/dto/ConsultaPlantaoAtualRespostaDTO.java` | Schema da resposta da consulta de plantão |
| `src/main/java/br/com/othiagob/cidadaobot/plantao/dto/PlantaoRespostaDTO.java` | Schema de uma escala de plantão |
| `src/main/java/br/com/othiagob/cidadaobot/plantao/dto/FarmaciaRespostaDTO.java` | Schema dos dados públicos da farmácia |
| `src/main/java/br/com/othiagob/cidadaobot/dto/RespostaApiDTO.java` | Schema do envelope padrão da API |
| `src/main/java/br/com/othiagob/cidadaobot/erro/ErroRespostaDTO.java` | Schema da resposta padronizada de erro |
| `RelatoriosCidadaoBot/relatorio-fase-12-openapi-dtos.md` | Relatório inicial da fase |
| `RelatoriosCidadaoBot/relatorio-fase-12-openapi-dtos-completo.md` | Relatório completo com trechos de código |

## 4. Controller documentado

### Diretório

```text
src/main/java/br/com/othiagob/cidadaobot/plantao/PlantaoController.java
```

### Imports adicionados

Foram adicionados imports do OpenAPI para documentar operações, parâmetros, respostas, conteúdo e schemas:

```java
import br.com.othiagob.cidadaobot.erro.ErroRespostaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
```

### Tag do controller

O controller foi agrupado no Swagger com a tag `Plantões`:

```java
@Tag(
    name = "Plantões",
    description = "Endpoints para consulta de farmácias de plantão em Ji-Paraná/RO.")
public class PlantaoController {
```

## 5. Endpoint `GET /api/plantoes`

### Método documentado

```java
buscarPlantaoPorData
```

### Documentação adicionada

O endpoint recebeu descrição da operação e responses compatíveis com o comportamento atual da API:

```java
@Operation(
    summary = "Consultar escala de plantão por data",
    description = "Consulta a escala de plantão para a data informada.")
@ApiResponse(
    responseCode = "200",
    description = "Consulta realizada com sucesso.",
    content = @Content(schema = @Schema(implementation = RespostaApiDTO.class)))
@ApiResponse(
    responseCode = "400",
    description = "Parâmetro inválido na requisição.",
    content = @Content(schema = @Schema(implementation = ErroRespostaDTO.class)))
@ApiResponse(
    responseCode = "500",
    description = "Erro interno inesperado.",
    content = @Content(schema = @Schema(implementation = ErroRespostaDTO.class)))
@GetMapping
```

### Parâmetro `data`

O parâmetro `data` foi documentado como obrigatório, com formato esperado `yyyy-MM-dd`:

```java
@Parameter(
        description = "Data obrigatória para consulta no formato yyyy-MM-dd.",
        required = true,
        example = "2026-05-19")
    @RequestParam
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate data
```

### Parâmetro `distrito`

O parâmetro `distrito` foi documentado como opcional:

```java
@Parameter(
        description = "Filtra opcionalmente a consulta pelo distrito da farmácia.",
        example = "Primeiro Distrito")
@RequestParam(required = false)
    @Pattern(regexp = ".*\\S.*", message = "O distrito não pode ser vazio.")
    String distrito
```

## 6. Endpoint `GET /api/plantoes/atual`

### Método documentado

```java
buscarPlantaoAtual
```

### Documentação adicionada

O endpoint foi documentado considerando a regra temporal oficial já existente na aplicação:

```java
@Operation(
    summary = "Consultar plantão atual",
    description =
        """
        Retorna as farmácias de plantão considerando
        a regra temporal oficial do sistema:

        19:00 até 07:00 do dia seguinte.
        """)
@ApiResponse(
    responseCode = "200",
    description = "Consulta realizada com sucesso.",
    content = @Content(schema = @Schema(implementation = RespostaApiDTO.class)))
@ApiResponse(
    responseCode = "400",
    description = "Parâmetro inválido na requisição.",
    content = @Content(schema = @Schema(implementation = ErroRespostaDTO.class)))
@ApiResponse(
    responseCode = "500",
    description = "Erro interno inesperado.",
    content = @Content(schema = @Schema(implementation = ErroRespostaDTO.class)))
@GetMapping("/atual")
```

### Parâmetro `distrito`

```java
@Parameter(
        description = "Filtra opcionalmente a consulta pelo distrito da farmácia.",
        example = "Segundo Distrito")
    @RequestParam(required = false)
    @Pattern(regexp = ".*\\S.*", message = "O distrito não pode ser vazio.")
    String distrito
```

### Observação sobre status 404

A documentação não registra `404` como resposta principal porque o comportamento atual da API não retorna `404` quando não existe escala. A API responde `200 OK` com mensagem indicando ausência de plantão.

## 7. DTO `ConsultaPlantaoAtualRespostaDTO`

### Diretório

```text
src/main/java/br/com/othiagob/cidadaobot/plantao/dto/ConsultaPlantaoAtualRespostaDTO.java
```

### Schema documentado

```java
@Schema(
    name = "ConsultaPlantaoAtualResposta",
    description = "Dados retornados pela consulta de farmácias de plantão.")
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
    List<PlantaoRespostaDTO> plantoes) {}
```

### Campos documentados

- `dataReferencia`: data usada na busca da escala.
- `plantaoAtivo`: indica se existe plantão ativo.
- `mensagem`: resumo textual do resultado.
- `plantoes`: lista de farmácias encontradas.

## 8. DTO `PlantaoRespostaDTO`

### Diretório

```text
src/main/java/br/com/othiagob/cidadaobot/plantao/dto/PlantaoRespostaDTO.java
```

### Schema documentado

```java
@Schema(
    name = "PlantaoResposta",
    description = "Informações de uma escala de plantão retornada pela API.")
public record PlantaoRespostaDTO(
    @Schema(description = "Data oficial da escala de plantão.", example = "2026-05-19")
        LocalDate dataPlantao,
    @Schema(description = "Horário de início do plantão.", example = "19:00") LocalTime iniciaAs,
    @Schema(description = "Horário de término do plantão.", example = "07:00") LocalTime terminaAs,
    @Schema(description = "Dados da farmácia responsável pelo plantão.") FarmaciaRespostaDTO farmacia,
    @Schema(
            description = "Mensagem textual pronta para exibição ao usuário.",
            example = "Farmácia Real está de plantão das 19:00 às 07:00.")
    String mensagem) {
```

### Campos documentados

- `dataPlantao`: data oficial da escala.
- `iniciaAs`: horário de início.
- `terminaAs`: horário de término.
- `farmacia`: dados públicos da farmácia responsável.
- `mensagem`: texto pronto para exibição.

## 9. DTO `FarmaciaRespostaDTO`

### Diretório

```text
src/main/java/br/com/othiagob/cidadaobot/plantao/dto/FarmaciaRespostaDTO.java
```

### Schema documentado

```java
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
```

### Campos documentados

- `nome`: nome da farmácia.
- `endereco`: endereço da farmácia.
- `bairro`: bairro.
- `distrito`: distrito de Ji-Paraná.
- `telefone`: telefone de contato.

## 10. DTO `RespostaApiDTO`

### Diretório

```text
src/main/java/br/com/othiagob/cidadaobot/dto/RespostaApiDTO.java
```

### Schema documentado

```java
@Schema(name = "RespostaApi", description = "Envelope padrão de resposta da API.")
public record RespostaApiDTO<T>(
    @Schema(
            description = "Indica se a requisição foi processada com sucesso.",
            example = "true")
        boolean sucesso,
    @Schema(description = "Mensagem resumida da resposta.", example = "Consulta realizada com sucesso.")
        String mensagem,
    @Schema(description = "Conteúdo principal da resposta.") T dados,
    @Schema(
            description = "Data e hora em que a resposta foi gerada.",
            example = "2026-05-19T21:30:00")
        LocalDateTime timestamp) {
```

### Campos documentados

- `sucesso`: indica sucesso ou falha no processamento.
- `mensagem`: resumo da resposta.
- `dados`: conteúdo principal.
- `timestamp`: data e hora da geração da resposta.

## 11. DTO `ErroRespostaDTO`

### Diretório

```text
src/main/java/br/com/othiagob/cidadaobot/erro/ErroRespostaDTO.java
```

### Schema documentado

```java
@Schema(name = "ErroResposta", description = "Resposta padronizada para erros tratados pela API.")
public record ErroRespostaDTO(
    @Schema(
            description = "Data e hora em que o erro foi registrado.",
            example = "2026-05-19T21:30:00")
        LocalDateTime timestamp,
    @Schema(description = "Código HTTP do erro.", example = "400") int status,
    @Schema(description = "Descrição curta do erro HTTP.", example = "Bad Request") String erro,
    @Schema(description = "Mensagem explicando o motivo do erro.", example = "O distrito não pode ser vazio.")
        String mensagem,
    @Schema(description = "Caminho da requisição que gerou o erro.", example = "/api/plantoes")
        String caminho) {}
```

### Campos documentados

- `timestamp`: data e hora do erro.
- `status`: código HTTP.
- `erro`: descrição curta do erro.
- `mensagem`: motivo do erro.
- `caminho`: rota que originou o erro.

## 12. Validação

O comando de teste informado para validação foi executado:

```bash
./mvnw clean test
```

Resultado informado e confirmado: todos os testes passaram.

Na execução realizada durante a implementação, o Maven retornou:

```text
BUILD SUCCESS
Tests run: 34, Failures: 0, Errors: 0, Skipped: 0
```

## 13. Como conferir no navegador

Com a aplicação rodando na porta configurada do projeto, a documentação pode ser conferida em:

```text
http://localhost:8081/swagger-ui/index.html
```

Também é possível consultar o documento OpenAPI bruto em:

```text
http://localhost:8081/v3/api-docs
```

## 14. Resultado esperado no Swagger

Após as alterações, o Swagger deve exibir:

- Grupo `Plantões` para os endpoints de consulta.
- Descrições mais claras para `GET /api/plantoes`.
- Descrições mais claras para `GET /api/plantoes/atual`.
- Parâmetros `data` e `distrito` com explicação e exemplos.
- Responses `200`, `400` e `500` documentadas.
- Schemas nomeados para os DTOs de sucesso.
- Schema documentado para o envelope padrão da API.
- Schema documentado para erros tratados.

## 15. Resumo técnico

A fase adicionou documentação OpenAPI aos pontos já existentes da API, mantendo o contrato funcional intacto. A principal correção de documentação foi alinhar as responses ao comportamento real dos endpoints, evitando documentar `404` como resposta principal quando a API retorna `200 OK` com mensagem de ausência de escala.

Nenhum endpoint foi criado, nenhuma entidade foi exposta e nenhuma regra de negócio foi modificada.
