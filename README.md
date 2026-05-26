# CidadãoBot API

API REST desenvolvida em Java com Spring Boot para consulta de farmácias de plantão em Ji-Paraná/RO.

O projeto nasceu como uma solução de utilidade pública para facilitar o acesso da população às informações de farmácias de plantão. Além da funcionalidade principal de consulta, a API também está sendo evoluída como um backend profissional, com arquitetura em camadas, testes automatizados, documentação, Docker, CI e preparação para integração com n8n e WhatsApp Cloud API.

---

## Objetivo do projeto

O CidadãoBot API tem como objetivo centralizar a regra de negócio das farmácias de plantão de Ji-Paraná/RO em uma API confiável.

A API deve ser a fonte oficial de verdade do sistema.

Isso significa que ferramentas externas, como n8n, WhatsApp, agentes de IA ou interfaces web, não devem calcular regras de plantão por conta própria. Elas apenas consomem os dados e respostas fornecidas pela API.

---

## Contexto

Ji-Paraná/RO possui farmácias organizadas por escala de plantão. A API permite consultar qual farmácia está de plantão em uma data específica ou no momento atual, considerando a regra temporal oficial do projeto.

Além disso, a API também possui uma funcionalidade de histórico de conversas, criada para registrar interações realizadas por usuários em integrações futuras com n8n e WhatsApp.

---

## Funcionalidades implementadas

- Consulta de plantão atual
- Consulta de plantão por data
- Filtro opcional por distrito
- Persistência de farmácias
- Persistência de escalas de plantão
- Persistência de histórico de conversas
- Registro de interações do CidadãoBot
- Consulta de histórico por telefone
- DTOs para entrada e saída de dados
- Tratamento global de erros
- Validações de entrada
- Documentação OpenAPI/Swagger
- Testes unitários
- Testes JPA
- Testes WebMvc
- Testes de integração com Spring Boot completo
- Testcontainers com PostgreSQL real
- Docker
- Docker Compose
- GitHub Actions CI
- Separação entre testes rápidos e testes de integração

---

## Tecnologias utilizadas

- Java 17
- Spring Boot 3.5
- Spring Web
- Spring Data JPA
- Bean Validation
- PostgreSQL
- Flyway
- Maven Wrapper
- JUnit 5
- Mockito
- H2 Database para testes rápidos
- Testcontainers
- Docker
- Docker Compose
- SpringDoc OpenAPI
- GitHub Actions

---

## Arquitetura

O projeto segue arquitetura em camadas:

```text
Requisição HTTP
    ↓
Controller
    ↓
Service
    ↓
Repository
    ↓
Banco de dados
```

Responsabilidades principais:

| Camada | Responsabilidade |
|---|---|
| Controller | Receber requisições HTTP, validar entrada básica e devolver respostas |
| Service | Executar regras de negócio e regras de aplicação |
| Repository | Acessar o banco de dados |
| Entity | Representar tabelas do banco |
| DTO | Definir os dados que entram e saem pela API |

A API não expõe entidades JPA diretamente. Todas as respostas públicas usam DTOs.

---

## Domínios principais

### Farmácia

Representa uma farmácia cadastrada no sistema.

Principais dados:

- nome
- endereço
- bairro
- distrito
- telefone

### Plantão

Representa a escala de plantão de uma farmácia em uma determinada data.

Principais dados:

- data do plantão
- horário de início
- horário de término
- farmácia responsável

### Conversa

Representa o histórico de uma interação realizada com o CidadãoBot.

Principais dados:

- telefone do usuário
- mensagem recebida
- resposta enviada
- intenção detectada
- distrito detectado
- data de referência
- origem da interação
- data de criação

---

## Regra temporal do plantão

A regra oficial do projeto considera que o plantão funciona em ciclo de 24 horas, iniciando às 07:00 e terminando às 07:00 do dia seguinte.

```text
07:00 de um dia → 07:00 do dia seguinte
```

Tabela de referência:

| Horário da consulta | Data de referência |
|---|---|
| 06:59 do dia 11 | dia 10 |
| 07:00 do dia 11 | dia 11 |
| 22:00 do dia 11 | dia 11 |
| 02:00 do dia 12 | dia 11 |

A lógica temporal fica centralizada na Service do domínio de plantão.

---

## Estrutura principal do projeto

```text
src/main/java/br/com/othiagob/cidadaobot
├── CidadaobotApplication.java
├── configuracao/
│   └── OpenApiConfig.java
├── dto/
│   └── RespostaApiDTO.java
├── erro/
│   └── TratadorGlobalDeErros.java
├── farmacia/
│   ├── Farmacia.java
│   └── FarmaciaRepository.java
├── plantao/
│   ├── EscalaPlantao.java
│   ├── EscalaPlantaoRepository.java
│   ├── PlantaoController.java
│   ├── PlantaoService.java
│   └── dto/
│       ├── ConsultaPlantaoAtualRespostaDTO.java
│       ├── FarmaciaRespostaDTO.java
│       └── PlantaoRespostaDTO.java
└── conversa/
    ├── Conversa.java
    ├── ConversaRepository.java
    ├── ConversaService.java
    ├── ConversaController.java
    └── dto/
        ├── ConversaRegistroRequestDTO.java
        └── ConversaRespostaDTO.java
```

---

## Banco de dados

O projeto usa PostgreSQL como banco principal.

O versionamento do banco é feito com Flyway.

As migrations ficam em:

```text
src/main/resources/db/migration/
```

Regra importante:

```text
Nunca alterar uma migration já aplicada.
Toda mudança no schema deve ser feita com uma nova migration.
```

---

## Principais tabelas

### farmacias

Armazena as farmácias cadastradas.

### escala_plantao

Armazena as escalas de plantão das farmácias.

### conversas

Armazena o histórico de interações realizadas com o CidadãoBot.

Campos principais:

| Campo | Descrição |
|---|---|
| id | Identificador da conversa |
| telefone_usuario | Telefone do usuário |
| mensagem_usuario | Mensagem recebida |
| resposta_enviada | Resposta enviada pelo CidadãoBot |
| intencao_detectada | Intenção interpretada pela IA ou fluxo externo |
| distrito_detectado | Distrito identificado na conversa |
| data_referencia | Data usada como referência na resposta |
| origem | Origem da interação |
| criada_em | Data e hora do registro |

---

## Configuração de ambiente

O projeto trabalha com profiles do Spring.

| Profile | Uso |
|---|---|
| dev | Desenvolvimento local |
| test | Testes automatizados rápidos |
| integration | Testes de integração |
| prod | Execução em Docker/produção |

---

## Como rodar em desenvolvimento

Suba a aplicação em modo dev:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

Por padrão, a API em dev roda em:

```text
http://localhost:8081
```

---

## Como rodar com Docker

Subir os containers:

```bash
docker compose up -d
```

Verificar containers:

```bash
docker compose ps
```

Ver logs da API:

```bash
docker compose logs -f api
```

Parar os containers:

```bash
docker compose down
```

Parar e remover volumes:

```bash
docker compose down -v
```

---

## Documentação Swagger

Com a aplicação rodando, acesse:

```text
http://localhost:8081/swagger-ui.html
```

ou, dependendo do ambiente:

```text
http://localhost:8080/swagger-ui.html
```

A documentação OpenAPI permite testar os endpoints diretamente pelo navegador.

---

## Padrão de resposta da API

Todas as respostas seguem o padrão `RespostaApiDTO`.

Exemplo de sucesso:

```json
{
  "sucesso": true,
  "mensagem": "Operação realizada com sucesso.",
  "dados": {},
  "timestamp": "2026-05-25T23:04:35.325037566"
}
```

Exemplo de erro:

```json
{
  "sucesso": false,
  "mensagem": "Mensagem de erro.",
  "dados": null,
  "timestamp": "2026-05-25T23:05:11.441381692"
}
```

---

# Endpoints de plantão

## Consultar plantão atual

```http
GET /api/plantoes/atual
```

Exemplo:

```bash
curl -s "http://localhost:8081/api/plantoes/atual" | jq
```

Com filtro por distrito:

```bash
curl -s "http://localhost:8081/api/plantoes/atual?distrito=Primeiro%20Distrito" | jq
```

Exemplo de resposta:

```json
{
  "sucesso": true,
  "mensagem": "Plantão ativo encontrado.",
  "dados": {
    "dataReferencia": "2026-05-25",
    "plantaoAtivo": true,
    "mensagem": "Plantão ativo encontrado.",
    "plantoes": [
      {
        "dataPlantao": "2026-05-25",
        "iniciaAs": "07:00:00",
        "terminaAs": "07:00:00",
        "farmacia": {
          "nome": "FARMACIA REAL",
          "endereco": "RUA DOS MINEIROS, 298 - PRÓXIMO À RODOVIÁRIA",
          "bairro": "CENTRO",
          "distrito": "Primeiro Distrito",
          "telefone": "3422-3491"
        },
        "mensagem": "FARMACIA REAL está de plantão das 07:00 às 07:00."
      }
    ]
  },
  "timestamp": "2026-05-25T23:00:00"
}
```

---

## Consultar plantão por data

```http
GET /api/plantoes?data=2026-05-25
```

Exemplo:

```bash
curl -s "http://localhost:8081/api/plantoes?data=2026-05-25" | jq
```

Com filtro por distrito:

```bash
curl -s "http://localhost:8081/api/plantoes?data=2026-05-25&distrito=Segundo%20Distrito" | jq
```

---

# Endpoints de conversas

A funcionalidade de conversas foi criada para registrar e consultar o histórico de interações do CidadãoBot.

Ela prepara o backend para integrações futuras com:

- n8n
- WhatsApp Cloud API
- agentes de IA
- dashboards internos
- auditoria de atendimento

Importante:

```text
O histórico de conversas não contém regra de plantão.
Ele apenas registra o que aconteceu em uma interação.
```

A regra principal de plantão continua no domínio `plantao`.

---

## Registrar uma interação

```http
POST /api/conversas
```

Exemplo:

```bash
curl -s -X POST "http://localhost:8081/api/conversas" \
  -H "Content-Type: application/json" \
  -d '{
    "telefoneUsuario": "5569999999999",
    "mensagemUsuario": "Qual farmácia está de plantão hoje?",
    "respostaEnviada": "Hoje a farmácia de plantão é FARMACIA REAL.",
    "intencaoDetectada": "CONSULTAR_PLANTAO_ATUAL",
    "distritoDetectado": "Primeiro Distrito",
    "dataReferencia": "2026-05-24",
    "origem": "WHATSAPP"
  }' | jq
```

Exemplo de resposta:

```json
{
  "sucesso": true,
  "mensagem": "Interação registrada com sucesso.",
  "dados": {
    "id": 4,
    "telefoneUsuario": "5569999999999",
    "mensagemUsuario": "Qual farmácia está de plantão hoje?",
    "respostaEnviada": "Hoje a farmácia de plantão é FARMACIA REAL.",
    "intencaoDetectada": "CONSULTAR_PLANTAO_ATUAL",
    "distritoDetectado": "Primeiro Distrito",
    "dataReferencia": "2026-05-24",
    "origem": "WHATSAPP",
    "criadaEm": "2026-05-25T23:04:35.301048"
  },
  "timestamp": "2026-05-25T23:04:35.325037566"
}
```

---

## Consultar histórico por telefone

```http
GET /api/conversas?telefone=5569999999999
```

Exemplo:

```bash
curl -s "http://localhost:8081/api/conversas?telefone=5569999999999" | jq
```

---

## Consultar histórico por telefone com limite

```http
GET /api/conversas?telefone=5569999999999&limite=10
```

Exemplo:

```bash
curl -s "http://localhost:8081/api/conversas?telefone=5569999999999&limite=10" | jq
```

Exemplo de resposta:

```json
{
  "sucesso": true,
  "mensagem": "Histórico de conversas consultado com sucesso.",
  "dados": [
    {
      "id": 4,
      "telefoneUsuario": "5569999999999",
      "mensagemUsuario": "Qual farmácia está de plantão hoje?",
      "respostaEnviada": "Hoje a farmácia de plantão é FARMACIA REAL.",
      "intencaoDetectada": "CONSULTAR_PLANTAO_ATUAL",
      "distritoDetectado": "Primeiro Distrito",
      "dataReferencia": "2026-05-24",
      "origem": "WHATSAPP",
      "criadaEm": "2026-05-25T23:04:35.301048"
    }
  ],
  "timestamp": "2026-05-25T23:04:44.250917542"
}
```

---

## Campos do cadastro de conversa

| Campo | Obrigatório | Descrição |
|---|---:|---|
| telefoneUsuario | Sim | Telefone do usuário que enviou a mensagem |
| mensagemUsuario | Sim | Mensagem recebida do usuário |
| respostaEnviada | Sim | Resposta enviada pelo CidadãoBot |
| intencaoDetectada | Não | Intenção detectada pela IA ou pelo fluxo externo |
| distritoDetectado | Não | Distrito identificado na conversa |
| dataReferencia | Não | Data usada como referência na resposta |
| origem | Sim | Origem da interação |

Exemplos de origem:

```text
WHATSAPP
N8N
API
TESTE
```

---

## Validações de conversas

Campos obrigatórios no cadastro:

- `telefoneUsuario`
- `mensagemUsuario`
- `respostaEnviada`
- `origem`

O parâmetro `limite` deve respeitar:

```text
mínimo: 1
máximo: 100
padrão: 10
```

Exemplo de erro de validação:

```json
{
  "sucesso": false,
  "mensagem": "telefoneUsuario: Telefone do usuário é obrigatório.; mensagemUsuario: Mensagem do usuário é obrigatória.; respostaEnviada: Resposta enviada é obrigatória.; origem: Origem é obrigatória.",
  "dados": null,
  "timestamp": "2026-05-25T23:05:11.441381692"
}
```

---

# Testes

O projeto possui testes em múltiplas camadas.

## Testes rápidos

Rodar todos os testes rápidos:

```bash
./mvnw test
```

Rodar um teste específico:

```bash
./mvnw test -Dtest=PlantaoServiceTest
```

Outro exemplo:

```bash
./mvnw test -Dtest=ConversaServiceTest
```

---

## Testes de integração

Rodar o ciclo completo:

```bash
./mvnw verify
```

Rodar teste de integração específico:

```bash
./mvnw verify -Dit.test=ConversaIntegrationIT
```

---

## Tipos de teste usados

| Tipo | Exemplo | Objetivo |
|---|---|---|
| Unitário | `PlantaoServiceTest`, `ConversaServiceTest` | Testar regra isolada |
| JPA | `EscalaPlantaoRepositoryTest`, `ConversaRepositoryTest` | Testar persistência e queries |
| WebMvc | `PlantaoControllerTest`, `ConversaControllerTest` | Testar contrato HTTP |
| Integração | `ConversaIntegrationIT` | Testar fluxo completo |

---

## Testes do domínio de conversas

A Fase 15 adicionou testes para a funcionalidade de histórico.

Arquivos principais:

```text
src/test/java/br/com/othiagob/cidadaobot/conversa/
├── ConversaServiceTest.java
├── ConversaRepositoryTest.java
├── ConversaControllerTest.java
└── ConversaIntegrationIT.java
```

Esses testes validam:

- registro de interação;
- conversão entre Entity e DTO;
- consulta por telefone;
- limite padrão;
- limite máximo;
- ordenação por data de criação;
- validação de entrada;
- tratamento de erros;
- fluxo completo da API.

---

# Integração com n8n e WhatsApp

A arquitetura prevista para integração é:

```text
Usuário no WhatsApp
    ↓
WhatsApp Cloud API
    ↓
n8n
    ↓
IA interpreta intenção
    ↓
n8n chama a API
    ↓
CidadãoBot API executa a regra
    ↓
n8n envia a resposta ao usuário
```

O n8n deve:

- receber mensagens;
- orquestrar o fluxo;
- chamar a API;
- enviar resposta ao usuário;
- registrar a conversa na API.

O n8n não deve:

- calcular plantão;
- decidir qual farmácia está de plantão;
- duplicar regra de negócio;
- substituir a API como fonte de verdade.

---

## Exemplo de uso futuro com n8n

Fluxo esperado:

1. Usuário pergunta no WhatsApp:
   ```text
   Qual farmácia está de plantão hoje?
   ```

2. n8n interpreta a intenção:
   ```json
   {
     "intencao": "CONSULTAR_PLANTAO_ATUAL",
     "distrito": null,
     "data": null
   }
   ```

3. n8n chama:
   ```http
   GET /api/plantoes/atual
   ```

4. API retorna o plantão.

5. n8n envia a resposta ao usuário.

6. n8n registra a interação:
   ```http
   POST /api/conversas
   ```

---

# Tratamento de erros

A API possui tratamento global de erros em:

```text
src/main/java/br/com/othiagob/cidadaobot/erro/TratadorGlobalDeErros.java
```

Esse tratador padroniza respostas para:

- erro de validação no body;
- parâmetro obrigatório ausente;
- parâmetro com formato inválido;
- violação de restrição;
- argumento inválido;
- erro interno inesperado.

Exemplo de erro por data inválida:

```bash
curl -s "http://localhost:8081/api/plantoes?data=17-05-2026" | jq
```

Resposta esperada:

```json
{
  "sucesso": false,
  "mensagem": "Data inválida. Formato esperado: AAAA-MM-DD.",
  "dados": null,
  "timestamp": "2026-05-25T23:00:00"
}
```

---

# CI com GitHub Actions

O projeto possui pipeline de integração contínua com GitHub Actions.

A CI valida:

- compilação;
- testes rápidos;
- testes de integração;
- build Maven;
- compatibilidade em matriz Java.

Arquivo principal:

```text
.github/workflows/build.yml
```

---

# Comandos úteis

## Rodar aplicação em dev

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

## Rodar testes rápidos

```bash
./mvnw test
```

## Rodar ciclo completo

```bash
./mvnw verify
```

## Build do projeto

```bash
./mvnw clean package
```

## Subir com Docker

```bash
docker compose up -d
```

## Ver logs da API

```bash
docker compose logs -f api
```

## Parar Docker

```bash
docker compose down
```

---

# Exemplos rápidos com curl

## Plantão atual

```bash
curl -s "http://localhost:8081/api/plantoes/atual" | jq
```

## Plantão atual por distrito

```bash
curl -s "http://localhost:8081/api/plantoes/atual?distrito=Primeiro%20Distrito" | jq
```

## Plantão por data

```bash
curl -s "http://localhost:8081/api/plantoes?data=2026-05-25" | jq
```

## Registrar conversa

```bash
curl -s -X POST "http://localhost:8081/api/conversas" \
  -H "Content-Type: application/json" \
  -d '{
    "telefoneUsuario": "5569999999999",
    "mensagemUsuario": "Qual farmácia está de plantão hoje?",
    "respostaEnviada": "Hoje a farmácia de plantão é FARMACIA REAL.",
    "intencaoDetectada": "CONSULTAR_PLANTAO_ATUAL",
    "distritoDetectado": "Primeiro Distrito",
    "dataReferencia": "2026-05-24",
    "origem": "WHATSAPP"
  }' | jq
```

## Consultar histórico

```bash
curl -s "http://localhost:8081/api/conversas?telefone=5569999999999&limite=10" | jq
```

---

# Decisões técnicas importantes

## Por que usar DTOs?

DTOs evitam que a API exponha diretamente as entidades JPA.

Isso ajuda a manter:

- segurança;
- controle do contrato HTTP;
- separação entre banco e API;
- liberdade para mudar a estrutura interna sem quebrar clientes externos.

## Por que usar Service Layer?

A Service concentra regras de negócio e regras de aplicação.

Isso evita Controllers com lógica demais e facilita testes unitários.

## Por que usar Flyway?

Flyway versiona o banco de dados.

Isso evita que o schema dependa de alterações automáticas do Hibernate e permite rastrear a evolução estrutural do projeto.

## Por que usar Testcontainers?

Testcontainers permite rodar testes de integração com PostgreSQL real.

Isso aumenta a confiança de que o comportamento validado em teste se aproxima do ambiente real.

## Por que histórico de conversas é separado de plantão?

Porque histórico é rastreabilidade.

Plantão é regra de negócio principal.

Misturar os dois domínios aumentaria acoplamento e deixaria o projeto mais difícil de evoluir.

---

# Status atual do projeto

Fases concluídas:

| Fase | Descrição |
|---:|---|
| 1 | Base Spring Boot, PostgreSQL e Flyway |
| 2 | Relacionamentos JPA |
| 3 | Service Layer e regra temporal |
| 4 | API REST e DTOs |
| 5 | Tratamento global de erros |
| 6 | Refatoração e robustez |
| 7 | Dados reais |
| 8 | Consulta por data |
| 9 | Docker e Docker Compose |
| 10 | Integração arquitetural com n8n |
| 11 | README profissional |
| 12 | OpenAPI/Swagger |
| 13 | GitHub Actions CI |
| 14 | Testes de integração profissionais |
| 15 | Persistência de conversas e histórico |

---

# Próximos passos possíveis

Possíveis evoluções futuras:

- integração real com n8n registrando conversas na API;
- integração oficial com WhatsApp Cloud API;
- logs estruturados;
- observabilidade;
- filtros de histórico por período;
- paginação mais completa;
- autenticação para endpoints administrativos;
- deploy em cloud;
- dashboard de atendimentos.

---

# Objetivo profissional

Este projeto também serve como portfólio técnico.

Ele demonstra conhecimentos de:

- Java;
- Spring Boot;
- REST APIs;
- JPA;
- PostgreSQL;
- Flyway;
- Docker;
- Testes automatizados;
- Testcontainers;
- GitHub Actions;
- documentação técnica;
- arquitetura em camadas;
- integração com ferramentas externas.

---

# Autor

Desenvolvido por Thiago Borghardt.

Projeto criado com foco em aprendizado prático de backend Java, construção de portfólio e preparação para entrevistas técnicas.
