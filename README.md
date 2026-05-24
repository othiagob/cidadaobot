# CidadãoBot API — Farmácias de Plantão

![Build e Testes](https://github.com/othiagob/cidadaobot/actions/workflows/build.yml/badge.svg)

API REST para consulta de farmácias de plantão em Ji-Paraná/RO.

O projeto nasceu de uma necessidade real: facilitar o acesso a uma informação simples, mas importante, principalmente em horários de urgência. Em vez de depender de escala impressa na porta de uma farmácia ou de publicações espalhadas em redes sociais, a proposta é centralizar os dados em uma API confiável.

Além da parte funcional, este projeto também é um laboratório de aprendizado backend com Java, Spring Boot, PostgreSQL, Flyway, Docker, testes automatizados, integração contínua e organização profissional de código.

A ideia futura é integrar esta API com n8n, WhatsApp Cloud API e IA para permitir consultas por mensagem. Mesmo nesse cenário, a regra de negócio continuará dentro da API.

---

## Sumário

- [Objetivo do Projeto](#objetivo-do-projeto)
- [Como Surgiu](#como-surgiu)
- [Arquitetura Geral](#arquitetura-geral)
- [Arquitetura Interna](#arquitetura-interna)
- [Regra de Negócio Principal](#regra-de-negócio-principal)
- [Stack Utilizada](#stack-utilizada)
- [Endpoints Disponíveis](#endpoints-disponíveis)
- [Exemplos de Resposta](#exemplos-de-resposta)
- [Como Rodar com Docker](#como-rodar-com-docker)
- [Como Rodar em Desenvolvimento Local](#como-rodar-em-desenvolvimento-local)
- [Profiles da Aplicação](#profiles-da-aplicação)
- [Banco de Dados e Migrations](#banco-de-dados-e-migrations)
- [Testes Automatizados](#testes-automatizados)
- [Integração Contínua](#integração-contínua)
- [Documentação OpenAPI](#documentação-openapi)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Decisões Técnicas](#decisões-técnicas)
- [O Que Este Projeto Demonstra](#o-que-este-projeto-demonstra)
- [Comandos Úteis](#comandos-úteis)
- [Status Atual](#status-atual)
- [Próximos Passos](#próximos-passos)
- [Autor](#autor)

---

## Objetivo do Projeto

### Objetivo funcional

Disponibilizar uma API REST para consultar farmácias de plantão em Ji-Paraná/RO.

A API permite consultar:

- o plantão atual;
- o plantão de uma data específica;
- o plantão filtrado por distrito.

### Objetivo técnico

Construir uma API backend com arquitetura em camadas, persistência relacional, versionamento de banco, testes automatizados, documentação OpenAPI, Docker e integração contínua.

### Objetivo de aprendizado

Praticar backend Java com foco em:

- Spring Boot;
- REST APIs;
- JPA;
- PostgreSQL;
- Flyway;
- DTOs;
- tratamento global de erros;
- testes unitários;
- testes WebMvc;
- testes JPA;
- testes de integração com banco real;
- Docker;
- GitHub Actions;
- boas práticas de arquitetura.

### Objetivo profissional

Usar um problema real como base para construir um projeto de portfólio, demonstrando evolução técnica em backend Java e preparação para entrevistas técnicas.

---

## Como Surgiu

A ideia surgiu da dificuldade de acessar rapidamente qual farmácia está de plantão em Ji-Paraná/RO.

Hoje, muitas vezes essa informação depende de escala impressa, publicações em redes sociais ou contato direto com farmácias. Isso funciona, mas não é prático em situações de urgência.

O objetivo do CidadãoBot API é transformar essa informação em um serviço digital consultável, organizado e confiável.

A proposta futura é permitir que uma pessoa envie uma mensagem pelo WhatsApp perguntando algo como:

```text
Qual farmácia está de plantão hoje?
```

O n8n poderá interpretar o fluxo, uma IA poderá entender a intenção, mas quem decide a resposta oficial será sempre a API.

---

## Arquitetura Geral

Fluxo planejado do produto completo:

```text
Usuário
  ↓
WhatsApp
  ↓
n8n
  ↓
API Spring Boot
  ↓
PostgreSQL
```

Responsabilidades:

- o WhatsApp será a interface de conversa;
- o n8n fará a orquestração do fluxo;
- a IA poderá interpretar a intenção da mensagem;
- a API Spring Boot aplicará as regras de negócio;
- o PostgreSQL armazenará farmácias e escalas oficiais.

A API é a fonte de verdade do sistema.

Nenhuma regra de negócio deve ficar no WhatsApp, no n8n ou na IA.

---

## Arquitetura Interna

A aplicação segue uma arquitetura em camadas:

```text
Controller
  ↓
Service
  ↓
Repository
  ↓
Banco de dados
```

### Controller

Responsável por:

- receber requisições HTTP;
- validar parâmetros básicos;
- chamar a camada Service;
- retornar respostas JSON padronizadas.

O Controller não deve conter regra de negócio.

### Service

Responsável por:

- concentrar regras de negócio;
- determinar a data de referência do plantão;
- validar regras do domínio;
- buscar dados por meio dos repositories;
- montar DTOs de resposta.

A regra temporal do plantão fica nesta camada.

### Repository

Responsável por:

- acessar o banco de dados;
- executar consultas com Spring Data JPA;
- retornar entidades JPA para a camada Service.

Repository não deve conter regra de negócio.

### DTOs

A API não retorna entidades JPA diretamente.

As respostas são expostas por DTOs, mantendo o contrato externo desacoplado do modelo interno do banco.

---

## Regra de Negócio Principal

A escala oficial de plantão funciona como um ciclo de 24 horas:

```text
07:00 de um dia até 07:00 do dia seguinte
```

A API usa 07:00 como horário de virada para determinar a data de referência do plantão.

Exemplos:

| Momento da consulta | Data de referência |
| --- | --- |
| 22:00 do dia 10 | dia 10 |
| 02:00 do dia 11 | dia 10 |
| 06:59 do dia 11 | dia 10 |
| 07:00 do dia 11 | dia 11 |
| 18:59 do dia 11 | dia 11 |
| 19:00 do dia 11 | dia 11 |

Essa regra fica na camada Service, no método responsável por determinar a data ativa do plantão.

---

## Stack Utilizada

| Tecnologia | Finalidade |
| --- | --- |
| Java 17 | Linguagem principal |
| Spring Boot 3.5 | Base da aplicação |
| Spring Web | Endpoints REST |
| Spring Data JPA | Persistência com repositories |
| PostgreSQL 15 | Banco principal |
| Flyway | Versionamento do banco de dados |
| Bean Validation | Validação de parâmetros |
| JUnit 5 | Testes automatizados |
| Mockito | Mocks em testes unitários e WebMvc |
| H2 | Banco em memória para testes rápidos |
| Testcontainers | Testes de integração com PostgreSQL real |
| Docker | Empacotamento da aplicação |
| Docker Compose | Subida da API com PostgreSQL |
| SpringDoc OpenAPI | Documentação Swagger |
| Maven Wrapper | Execução padronizada do Maven |
| GitHub Actions | Integração contínua |

---

## Endpoints Disponíveis

### Consultar plantão atual

```http
GET /api/plantoes/atual
```

Exemplo com Docker/prod:

```bash
curl -s "http://localhost:8080/api/plantoes/atual" | jq
```

Exemplo em dev/local:

```bash
curl -s "http://localhost:8081/api/plantoes/atual" | jq
```

Com filtro por distrito:

```bash
curl -s "http://localhost:8081/api/plantoes/atual?distrito=Primeiro%20Distrito" | jq
```

### Consultar plantão por data

```http
GET /api/plantoes?data=AAAA-MM-DD
```

Exemplo:

```bash
curl -s "http://localhost:8081/api/plantoes?data=2026-05-17" | jq
```

Com filtro por distrito:

```bash
curl -s "http://localhost:8081/api/plantoes?data=2026-05-17&distrito=Segundo%20Distrito" | jq
```

---

## Exemplos de Resposta

### Resposta de sucesso

```json
{
  "sucesso": true,
  "mensagem": "Plantão encontrado para a data informada.",
  "dados": {
    "dataReferencia": "2026-05-17",
    "plantaoAtivo": true,
    "mensagem": "Plantão encontrado para a data informada.",
    "plantoes": [
      {
        "dataPlantao": "2026-05-17",
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
  "timestamp": "2026-05-18T10:30:00"
}
```

### Resposta sem escala cadastrada

Quando não há escala cadastrada para a data consultada, a API não inventa dados.

```json
{
  "sucesso": true,
  "mensagem": "Não encontrei escala de plantão cadastrada para esta data no sistema.",
  "dados": {
    "dataReferencia": "2026-05-17",
    "plantaoAtivo": true,
    "mensagem": "Não encontrei escala de plantão cadastrada para esta data no sistema.",
    "plantoes": []
  },
  "timestamp": "2026-05-18T10:30:00"
}
```

### Resposta de erro

Exemplo de data inválida:

```bash
curl -s "http://localhost:8081/api/plantoes?data=17-05-2026" | jq
```

Resposta:

```json
{
  "sucesso": false,
  "mensagem": "Data inválida. Formato esperado: AAAA-MM-DD.",
  "dados": null,
  "timestamp": "2026-05-18T10:30:00"
}
```

---

## Como Rodar com Docker

Pré-requisitos:

- Docker;
- Docker Compose;
- Git;
- jq, opcional, mas recomendado para visualizar JSON no terminal.

Clone o repositório:

```bash
git clone https://github.com/othiagob/cidadaobot.git
cd cidadaobot
```

Crie o arquivo de ambiente:

```bash
cp .env.example .env
nvim .env
```

Suba a aplicação com Docker Compose:

```bash
docker compose up -d
```

Verifique os containers:

```bash
docker compose ps
```

Acompanhe os logs da API:

```bash
docker compose logs -f api
```

Teste a API:

```bash
curl -s "http://localhost:8080/api/plantoes/atual" | jq
```

Para parar os containers:

```bash
docker compose down
```

Para parar e remover os volumes do banco local:

```bash
docker compose down -v
```

Use `down -v` com cuidado, pois isso remove os dados persistidos localmente.

---

## Como Rodar em Desenvolvimento Local

Pré-requisitos:

- Java 17;
- Docker ou PostgreSQL local;
- Maven Wrapper já incluído no projeto;
- jq opcional.

No profile `dev`, a aplicação roda em:

```text
http://localhost:8081
```

Antes de iniciar a API localmente, mantenha um PostgreSQL disponível com as configurações esperadas pelo profile `dev`.

Execute:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

Teste:

```bash
curl -s "http://localhost:8081/api/plantoes/atual" | jq
```

Consulta por data:

```bash
curl -s "http://localhost:8081/api/plantoes?data=2026-05-17" | jq
```

---

## Profiles da Aplicação

### dev

Ambiente local de desenvolvimento.

Características:

- usa PostgreSQL;
- roda na porta `8081`;
- Flyway ativo;
- Hibernate valida o schema;
- logs mais úteis para desenvolvimento.

### test

Ambiente usado pelos testes rápidos.

Características:

- usa H2 em memória;
- não depende de Docker;
- usado em testes unitários, JPA e WebMvc;
- executado com `./mvnw test`.

### integration

Ambiente usado pelos testes de integração.

Características:

- usa PostgreSQL real via Testcontainers;
- aplica migrations Flyway reais;
- sobe o contexto completo do Spring Boot;
- valida a API em um ambiente próximo ao de produção;
- executado no ciclo `verify`.

### prod

Ambiente usado no Docker e preparado para produção.

Características:

- usa PostgreSQL;
- lê configurações por variáveis de ambiente;
- Flyway ativo;
- Hibernate em modo `validate`;
- roda na porta `8080`.

---

## Banco de Dados e Migrations

O PostgreSQL é o banco principal da aplicação.

O Flyway controla a estrutura do banco por meio dos arquivos em:

```text
src/main/resources/db/migration
```

Migrations existentes:

```text
V1__criar_tabela_farmacias.sql
V2__criar_tabela_escala_plantao.sql
V3__adicionar_restricoes_escala_plantao.sql
V4__adicionar_restricao_farmacia.sql
V5__inserir_farmacias_iniciais.sql
V6__inserir_escalas_maio_junho_2026.sql
V7__ajustar_horario_padrao_plantao.sql
```

Regra importante:

```text
Migration já aplicada não deve ser editada.
```

Se for necessário alterar algo no banco, deve ser criada uma nova migration.

Em `dev` e `prod`, o Hibernate não cria nem altera tabelas automaticamente. Ele apenas valida o schema:

```properties
spring.jpa.hibernate.ddl-auto=validate
```

---

## Testes Automatizados

O projeto possui testes em múltiplas camadas.

### Testes unitários

Validam regras de negócio isoladas.

Exemplo:

```text
PlantaoServiceTest
```

Cobrem principalmente:

- regra temporal do plantão;
- data de referência;
- comportamento com e sem dados;
- filtro por distrito.

### Testes JPA

Validam persistência e queries com Spring Data JPA.

Exemplos:

```text
FarmaciaRepositoryTest
EscalaPlantaoRepositoryTest
```

Cobrem:

- mapeamento das entidades;
- relacionamento entre farmácia e escala;
- consultas por data;
- consultas por data e distrito.

### Testes WebMvc

Validam a camada HTTP de forma isolada.

Exemplo:

```text
PlantaoControllerTest
```

Cobrem:

- endpoints REST;
- resposta JSON padronizada;
- validação de parâmetros;
- erros de entrada;
- contrato com `RespostaApiDTO`.

### Testes de integração

Validam o fluxo completo da aplicação.

Exemplo:

```text
PlantaoControllerIT
```

Durante esses testes:

- o Testcontainers inicia um PostgreSQL temporário;
- o Flyway aplica as migrations reais;
- o Spring Boot sobe com contexto completo;
- o Tomcat roda em porta aleatória;
- a API é chamada via HTTP real;
- Controller, Service, Repository e Banco são testados juntos.

Fluxo validado:

```text
HTTP real
  ↓
Controller real
  ↓
Service real
  ↓
Repository real
  ↓
PostgreSQL real
  ↓
JSON real
```

### Rodar testes rápidos

```bash
./mvnw test
```

Esse comando executa testes unitários, JPA e WebMvc.

### Rodar validação completa

```bash
./mvnw verify
```

Esse comando executa os testes rápidos e também os testes de integração com Testcontainers.

### Rodar somente o teste de integração

```bash
./mvnw verify -Dit.test=PlantaoControllerIT
```

---

## Integração Contínua

O projeto utiliza GitHub Actions para validação automática.

A pipeline atual executa:

- validação de compilação Maven;
- testes automatizados;
- build da aplicação;
- matriz com Java 17 e Java 21.

Fluxo geral:

```text
Compilação
  ↓
Testes
  ↓
Build
```

A cada push ou pull request para a branch `main`, a pipeline valida se o projeto continua compilando e se os testes passam.

---

## Documentação OpenAPI

A API possui documentação automática com SpringDoc OpenAPI.

Com a aplicação rodando em Docker/prod:

```text
http://localhost:8080/swagger-ui.html
```

Com a aplicação rodando em dev/local:

```text
http://localhost:8081/swagger-ui.html
```

A documentação permite visualizar os endpoints disponíveis, parâmetros aceitos e respostas esperadas.

---

## Estrutura do Projeto

```text
src
├── main
│   ├── java
│   │   └── br/com/othiagob/cidadaobot
│   │       ├── configuracao
│   │       ├── dto
│   │       ├── erro
│   │       ├── farmacia
│   │       └── plantao
│   │           └── dto
│   └── resources
│       ├── application.properties
│       ├── application-dev.properties
│       ├── application-prod.properties
│       └── db/migration
└── test
    ├── java
    │   └── br/com/othiagob/cidadaobot
    │       ├── farmacia
    │       ├── integracao
    │       └── plantao
    └── resources
        ├── application-test.properties
        ├── application-integration.properties
        ├── docker-java.properties
        └── mockito-extensions
```

Pacotes principais:

| Pacote | Responsabilidade |
| --- | --- |
| `configuracao` | Configurações da aplicação |
| `dto` | DTOs globais |
| `erro` | Tratamento global de erros |
| `farmacia` | Domínio de farmácias |
| `plantao` | Domínio de escalas e consultas de plantão |
| `plantao/dto` | DTOs específicos de plantão |
| `integracao` | Testes de integração com ambiente real |

---

## Decisões Técnicas

### Java 17

Versão estável, madura e amplamente usada em projetos backend com Spring Boot.

### Spring Boot

Facilita a criação de APIs REST, configuração da aplicação, injeção de dependência, integração com banco e testes.

### PostgreSQL

Banco relacional confiável, com boa aderência a aplicações reais e ambientes de produção.

### Flyway

Mantém o histórico do banco versionado e reproduzível.

### JPA

Permite trabalhar com entidades Java e repositories, mantendo integração com o banco relacional.

### DTOs

Evitam expor entidades JPA diretamente e deixam o contrato externo da API mais estável.

### Tratamento global de erros

Centraliza erros em uma única camada e mantém respostas padronizadas.

### Docker

Permite subir API e banco de forma reproduzível.

### Testcontainers

Permite testar a aplicação contra um PostgreSQL real descartável, aproximando os testes do ambiente de produção.

### Separação entre `test` e `verify`

O projeto separa testes rápidos e testes de integração:

```text
./mvnw test
```

para validação rápida durante desenvolvimento.

```text
./mvnw verify
```

para validação completa com banco real e ambiente mais próximo de produção.

---

## O Que Este Projeto Demonstra

Este projeto demonstra conhecimentos práticos em:

- criação de API REST com Spring Boot;
- modelagem de domínio;
- separação de responsabilidades;
- persistência com JPA;
- uso de PostgreSQL;
- versionamento de banco com Flyway;
- criação de DTOs;
- tratamento global de erros;
- validação de parâmetros;
- testes automatizados em múltiplas camadas;
- testes de integração com Testcontainers;
- Docker e Docker Compose;
- profiles por ambiente;
- documentação OpenAPI;
- integração contínua com GitHub Actions;
- organização de projeto para portfólio.

Também demonstra uma preocupação importante: não apenas fazer funcionar, mas entender, testar, documentar e conseguir explicar as decisões técnicas.

---

## Comandos Úteis

### Rodar em desenvolvimento

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

### Rodar com Docker

```bash
docker compose up -d
```

### Ver logs da API

```bash
docker compose logs -f api
```

### Parar containers

```bash
docker compose down
```

### Rodar testes rápidos

```bash
./mvnw test
```

### Rodar validação completa

```bash
./mvnw verify
```

### Gerar build

```bash
./mvnw clean package
```

### Testar endpoint com curl e jq

```bash
curl -s "http://localhost:8081/api/plantoes/atual" | jq
```

---

## Status Atual

- [x] API REST funcional
- [x] PostgreSQL configurado
- [x] Flyway configurado
- [x] Entidades JPA criadas
- [x] DTOs padronizados
- [x] Service Layer consolidada
- [x] Tratamento global de erros
- [x] Consulta de plantão atual
- [x] Consulta de plantão por data
- [x] Filtro por distrito
- [x] Docker configurado
- [x] Docker Compose configurado
- [x] Profiles `dev`, `test`, `integration` e `prod`
- [x] Swagger/OpenAPI configurado
- [x] GitHub Actions configurado
- [x] Testes unitários
- [x] Testes JPA
- [x] Testes WebMvc
- [x] Testes de integração com Testcontainers
- [x] Separação entre testes rápidos e testes de integração
- [x] README atualizado

---

## Próximos Passos

### Curto prazo

- ajustar a pipeline do GitHub Actions para considerar o ciclo `verify`;
- revisar documentação conforme a evolução do projeto;
- manter os exemplos de uso atualizados.

### Médio prazo

- integrar com n8n;
- preparar contrato de integração para WhatsApp;
- criar persistência de histórico de conversas;
- melhorar logs da aplicação.

### Longo prazo

- deploy em cloud;
- observabilidade;
- monitoramento;
- alertas;
- integração oficial com WhatsApp Cloud API.

---

## Autor

Desenvolvido por Thiago Borghardt.

Projeto criado como laboratório de aprendizado backend com Java e Spring Boot, voltado para prática real, portfólio técnico e preparação para entrevistas.

