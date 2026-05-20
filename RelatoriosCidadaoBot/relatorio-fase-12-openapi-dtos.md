# Relatório — Fase 12 OpenAPI DTOs

## Objetivo

Foram adicionadas annotations OpenAPI/Swagger para melhorar a documentação dos endpoints REST e dos schemas exibidos pela API no Swagger UI.

## Arquivos alterados

- `src/main/java/br/com/othiagob/cidadaobot/plantao/PlantaoController.java`
- `src/main/java/br/com/othiagob/cidadaobot/plantao/dto/ConsultaPlantaoAtualRespostaDTO.java`
- `src/main/java/br/com/othiagob/cidadaobot/plantao/dto/PlantaoRespostaDTO.java`
- `src/main/java/br/com/othiagob/cidadaobot/plantao/dto/FarmaciaRespostaDTO.java`
- `src/main/java/br/com/othiagob/cidadaobot/dto/RespostaApiDTO.java`
- `src/main/java/br/com/othiagob/cidadaobot/erro/ErroRespostaDTO.java`
- `RelatoriosCidadaoBot/relatorio-fase-12-openapi-dtos.md`

## O que foi documentado

- Controller `PlantaoController`, com tag OpenAPI para agrupar os endpoints de plantão.
- Parâmetros `data` e `distrito` dos endpoints existentes, incluindo obrigatoriedade, formato esperado e exemplos.
- Responses 200, 400 e 500 dos endpoints `GET /api/plantoes` e `GET /api/plantoes/atual`.
- DTOs de sucesso usados nas consultas de plantão.
- DTO de erro padronizado da API.
- Envelope padrão de resposta da API.

## O que não foi alterado

- Nenhuma regra de negócio foi alterada.
- Nenhum endpoint novo foi criado.
- Nenhuma migration foi criada.
- Nenhuma entity foi exposta.
- Nenhum Service ou Repository foi alterado.

## Validação

Comando executado:

```bash
./mvnw clean test
```

Resultado: build com sucesso. Foram executados 34 testes, com 0 falhas, 0 erros e 0 testes ignorados.

## Como conferir no navegador

- `http://localhost:8081/swagger-ui/index.html`
- `http://localhost:8081/v3/api-docs`
