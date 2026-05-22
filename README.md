# CidadãoBot API — Farmácias de Plantão

![Build e Testes](https://github.com/othiagob/cidadaobot/actions/workflows/build.yml/badge.svg)

API REST para consultar farmácias de plantão em Ji-Paraná/RO.

O projeto nasceu de uma necessidade real: facilitar o acesso a uma informação simples, mas importante, principalmente em horários de urgência. Em vez de depender de uma escala impressa na porta de uma farmácia ou de publicações em redes sociais, a ideia é centralizar os dados em uma API e permitir consultas mais práticas.

Além da parte funcional, este projeto também é um laboratório de aprendizado backend com Java, Spring Boot, banco de dados, testes automatizados, Docker e organização de código.

No futuro, a API poderá ser integrada com n8n, WhatsApp e IA para permitir consultas por mensagem. Mesmo assim, a regra de negócio deve continuar dentro da API.

## Como Surgiu

A ideia surgiu da necessidade de acessar facilmente qual farmácia está de plantão. Hoje, a forma mais segura costuma ser ir até uma farmácia para ver a escala impressa na porta ou acompanhar publicações em redes sociais.

O objetivo do projeto é tornar essa consulta mais prática e acessível para a população, principalmente em horários críticos ou situações de urgência. A proposta futura é usar o WhatsApp como canal de consulta, por ser uma ferramenta já presente no dia a dia das pessoas.

## Objetivos

**Objetivo funcional**

Disponibilizar uma API para consultar farmácias de plantão por data, distrito e plantão atual.

**Objetivo técnico**

Construir uma API REST com Java 17, Spring Boot, PostgreSQL, Flyway, JPA, Docker e testes automatizados.

**Objetivo de aprendizado**

Praticar backend de forma progressiva, passando por modelagem de domínio, camadas da aplicação, persistência, validação, tratamento de erros, profiles e testes.

**Objetivo profissional**

Usar um problema real para demonstrar evolução em backend Java em um projeto de portfólio e preparação para entrevistas técnicas.

## Arquitetura Geral

Fluxo planejado para o produto completo:

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

O WhatsApp será a interface de conversa com o usuário. O n8n poderá orquestrar o fluxo da conversa. Uma IA poderá interpretar a intenção da mensagem. A API Spring Boot aplica as regras de negócio e consulta os dados oficiais. O PostgreSQL guarda farmácias e escalas de plantão.

A API é a fonte de verdade do sistema.

Nenhuma regra de negócio deve ficar no WhatsApp, no n8n ou na IA.


## Arquitetura Interna da API

```text
Controller
  ↓
Service
  ↓
Repository
  ↓
Banco de dados
```

**Controller**

- recebe requisições HTTP;
- valida parâmetros básicos;
- retorna respostas JSON.

**Service**

- concentra regras de negócio;
- calcula a data de referência do plantão;
- decide qual escala consultar.

**Repository**

- acessa o banco de dados;
- usa Spring Data JPA;
- não contém regra de negócio.

## Stack Utilizada

| Tecnologia | Finalidade |
| --- | --- |
| Java 17 | Linguagem principal |
| Spring Boot 3.5 | Base da aplicação |
| Spring Web | Criação dos endpoints REST |
| Spring Data JPA | Persistência com repositories |
| PostgreSQL | Banco principal |
| Flyway | Versionamento do banco |
| JUnit 5 | Testes automatizados |
| Mockito | Mocks em testes de unidade |
| H2 | Banco em memória para testes |
| Docker | Empacotamento da aplicação |
| Docker Compose | Subida da API com PostgreSQL |
| Maven Wrapper | Execução do Maven sem instalação manual |

## Integração Contínua (CI)

O projeto utiliza GitHub Actions para validação automática da aplicação.

A pipeline executa automaticamente:

- Validação de compilação Maven
- Testes automatizados
- Geração do build da aplicação
- Compatibilidade com Java 17 e Java 21

A cada push ou pull request para a branch `main`, a pipeline garante que o projeto continue íntegro e compilável.

Fluxo da pipeline:

Compilação → Testes → Build


## Regra de Negócio Principal

No estado atual do código, a escala oficial de plantão funciona como plantão de 24 horas:

```text
07:00 até 07:00 do dia seguinte
```

A API usa 07:00 como horário de virada para determinar a data de referência do plantão.

Exemplos:

- 22:00 do dia 10 -> plantão do dia 10
- 02:00 do dia 11 -> plantão do dia 10
- 06:59 do dia 11 -> plantão do dia 10
- 07:00 do dia 11 -> plantão do dia 11
- 18:59 do dia 11 -> plantão do dia 11
- 19:00 do dia 11 -> plantão do dia 11

Essa regra fica na camada Service, em `PlantaoService`.

## Endpoints Disponíveis

### GET /api/plantoes/atual

Consulta o plantão atual com base no horário da requisição.

Docker/prod:

```bash
curl -s "http://localhost:8080/api/plantoes/atual" | jq
```

Dev/local:

```bash
curl -s "http://localhost:8081/api/plantoes/atual" | jq
```

Com filtro por distrito:

```bash
curl -s "http://localhost:8081/api/plantoes/atual?distrito=Primeiro%20Distrito" | jq
```

### GET /api/plantoes?data=AAAA-MM-DD

Consulta a escala cadastrada para uma data específica.

```bash
curl -s "http://localhost:8081/api/plantoes?data=2026-05-17" | jq
```

Com filtro por distrito:

```bash
curl -s "http://localhost:8081/api/plantoes?data=2026-05-17&distrito=Segundo%20Distrito" | jq
```

## Exemplo de Resposta JSON

Exemplo baseado no contrato atual dos DTOs:

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

## Como Rodar o Projeto

Pré-requisitos:

- Java 17
- Docker
- Docker Compose
- Git
- jq

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

Não exponha credenciais reais no repositório. O arquivo `.env.example` serve apenas como modelo.

## Rodando com Docker

Docker Compose sobe a API e o PostgreSQL. Neste modo, a API fica em `localhost:8080`.

```bash
docker compose up -d
docker compose ps
docker compose logs -f api
```

Teste:

```bash
curl -s "http://localhost:8080/api/plantoes/atual" | jq
```

Para parar:

```bash
docker compose down
```

## Rodando em Desenvolvimento Local

No profile `dev`, a aplicação usa `localhost:8081`.

Antes de rodar localmente, mantenha um PostgreSQL disponível com as configurações esperadas pelo profile `dev` ou configure as variáveis de ambiente.

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

Teste:

```bash
curl -s "http://localhost:8081/api/plantoes/atual" | jq
```

## Profiles da Aplicação

**dev**

Ambiente local de desenvolvimento. Usa PostgreSQL, Flyway ativo, logs mais detalhados e porta `8081`.

**test**

Ambiente dos testes automatizados. Usa H2 em memória, Flyway desativado e `ddl-auto=create-drop`.

**prod**

Ambiente usado no Docker. Lê configurações por variáveis de ambiente, usa PostgreSQL, Flyway ativo e Hibernate em modo de validação.

## Banco de Dados e Migrations

O PostgreSQL é usado como banco principal da aplicação.

O Flyway controla a estrutura do banco por meio dos arquivos em `src/main/resources/db/migration`.

O Hibernate não cria tabelas no ambiente `dev` ou `prod`; ele apenas valida se o schema está compatível com as entidades:

```properties
spring.jpa.hibernate.ddl-auto=validate
```

Migrations já aplicadas não devem ser editadas. Alterações futuras no banco devem ser feitas criando novas migrations.

## Testes

Execute:

```bash
./mvnw test
```

Os testes atuais validam:

- regra temporal da data de referência;
- camada Service;
- repositories com JPA;
- endpoints HTTP;
- filtro por distrito;
- respostas quando não há dados;
- erros de parâmetros inválidos.

## Estrutura do Projeto

```text
src
├── main
│   ├── java
│   │   └── br/com/othiagob/cidadaobot
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
    │       └── plantao
    └── resources
        └── application-test.properties
```

## Decisões Técnicas

**Java 17**

Versão estável e muito usada em projetos Spring Boot atuais.

**Spring Boot**

Facilita a criação da API REST, configuração da aplicação e integração com banco de dados.

**PostgreSQL**

Banco relacional confiável para armazenar farmácias e escalas oficiais.

**Flyway**

Permite versionar o banco de dados com histórico claro das alterações.

**DTOs**

Evitam expor diretamente as entidades JPA nas respostas da API.

**Docker**

Ajuda a rodar a API e o banco com um ambiente mais próximo do real.

**Testes automatizados**

Ajudam a validar a regra de negócio e reduzem o risco de quebrar comportamentos já implementados.

## O Que Aprendi

Durante o desenvolvimento deste projeto, pratiquei conceitos importantes de backend como modelagem de domínio, criação de endpoints REST, separação em camadas, persistência com JPA, versionamento de banco com Flyway, testes automatizados e configuração de ambientes com Docker.

Também ficou mais claro como uma API pode ser a base de um produto maior, mesmo quando a interface final ainda será construída com outras ferramentas, como WhatsApp, n8n e IA.

## Status Atual

- [x] API REST funcionando
- [x] PostgreSQL configurado
- [x] Flyway configurado
- [x] Docker Compose configurado
- [x] Profiles `dev`, `test` e `prod` configurados
- [x] Testes automatizados configurados
- [x] Configuração externalizada
- [x] `spring.jpa.open-in-view=false`
- [x] README profissional concluído

## Próximos Passos

**Curto prazo**

- revisar README conforme a evolução do código;
- adicionar Swagger/OpenAPI;
- melhorar exemplos de uso.

**Médio prazo**

- integrar com n8n;
- integrar com WhatsApp;
- documentar o fluxo da integração.

**Longo prazo**

- fazer deploy em cloud;
- configurar CI/CD;
- adicionar observabilidade;
- adicionar monitoramento.

## Autor

Desenvolvido por Thiago Borghardt como projeto de aprendizado backend com Java e Spring Boot, voltado para portfólio, prática real e preparação para entrevistas técnicas.


