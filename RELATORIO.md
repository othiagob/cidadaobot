# Relatorio de Correcoes - FarmaciaRepositoryTest

## Contexto

O objetivo era verificar e corrigir os erros ao executar o teste relacionado ao repositorio de farmacias.

Arquivo informado inicialmente:

```text
src/test/java/br/com/othiagob/cidadaobot/farmacia/FarmaciaRepositoryTest.java
```

Durante a verificacao foi identificado que esse arquivo ainda nao existia nesse caminho. O teste estava localizado em:

```text
src/main/java/br/com/othiagob/cidadaobot/farmacia/FarmaciaRepositoryTest.java
```

Isso fazia com que uma classe de teste fosse compilada como codigo principal da aplicacao.

## Erro Encontrado

Ao executar:

```bash
./mvnw test
```

o Maven falhava na fase de compilacao principal com erros como:

```text
package org.assertj.core.api does not exist
package org.junit.jupiter.api does not exist
package org.springframework.boot.test.context does not exist
cannot find symbol: class SpringBootTest
cannot find symbol: class Test
cannot find symbol: class DisplayName
```

## Causa Raiz

O teste estava dentro de `src/main/java`.

No Maven, essa pasta representa o codigo principal da aplicacao. As dependencias de teste, como JUnit, AssertJ e Spring Boot Test, estao declaradas no `pom.xml` com escopo `test`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

Dependencias com escopo `test` nao ficam disponiveis durante a compilacao de `src/main/java`. Por isso o compilador nao encontrava as classes de teste.

## Correcoes Aplicadas

### 1. Movido o teste para o diretorio correto

O arquivo de teste foi removido de:

```text
src/main/java/br/com/othiagob/cidadaobot/farmacia/FarmaciaRepositoryTest.java
```

E recriado em:

```text
src/test/java/br/com/othiagob/cidadaobot/farmacia/FarmaciaRepositoryTest.java
```

Esse e o local correto para testes automatizados em projetos Maven.

### 2. Alterado o tipo de teste para `@DataJpaTest`

Antes, o teste usava:

```java
@SpringBootTest
```

Foi alterado para:

```java
@DataJpaTest(showSql = false)
@ActiveProfiles("test")
```

Motivo:

- `@DataJpaTest` e mais adequado para testar repositorios JPA.
- Ele carrega apenas a camada de persistencia necessaria para o teste.
- O teste fica mais rapido e mais focado.
- O perfil `test` permite usar configuracoes especificas para o ambiente de testes.

### 3. Inseridos dados dentro do proprio teste

O teste anterior consultava diretamente:

```java
farmaciaRepository.findByDistrito("Primeiro Distrito");
farmaciaRepository.findByBairro("Centro");
```

E esperava que o banco ja tivesse dados cadastrados.

Isso tornava o teste fragil, pois ele dependia do estado externo do banco.

Agora cada teste cria os proprios registros antes de consultar:

```java
Farmacia farmaciaPrimeiroDistrito =
    new Farmacia("Farmacia Centro", "Rua A, 123", "Centro", "Primeiro Distrito", "69999990000");

Farmacia farmaciaSegundoDistrito =
    new Farmacia("Farmacia Sul", "Rua B, 456", "Sul", "Segundo Distrito", "69999990001");

farmaciaRepository.saveAll(List.of(farmaciaPrimeiroDistrito, farmaciaSegundoDistrito));
```

Depois disso, o teste executa a busca e valida o resultado:

```java
List<Farmacia> farmacias = farmaciaRepository.findByDistrito("Primeiro Distrito");

assertThat(farmacias)
    .isNotEmpty()
    .allMatch(farmacia -> farmacia.getDistrito().equals("Primeiro Distrito"));
```

### 4. Adicionado banco H2 para testes

Foi adicionada a dependencia do H2 no `pom.xml`:

```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>test</scope>
</dependency>
```

Motivo:

- Permite rodar testes com banco em memoria.
- Evita depender de um PostgreSQL local rodando.
- Torna os testes mais simples de executar em qualquer ambiente.

### 5. Criado arquivo de configuracao para o perfil de teste

Foi criado:

```text
src/test/resources/application-test.properties
```

Conteudo:

```properties
spring.datasource.url=jdbc:h2:mem:cidadaobot;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false

spring.flyway.enabled=true
```

Motivo:

- O H2 roda em memoria durante os testes.
- `MODE=PostgreSQL` aproxima o comportamento do H2 ao PostgreSQL.
- O Flyway continua ativo para validar a migration real do projeto.
- `ddl-auto=validate` garante que a entidade JPA esta coerente com o schema criado pela migration.

### 6. Ajustado o teste de contexto da aplicacao

O arquivo:

```text
src/test/java/br/com/othiagob/cidadaobot/CidadaobotApplicationTests.java
```

foi ajustado para usar o perfil de teste:

```java
@SpringBootTest
@ActiveProfiles("test")
class CidadaobotApplicationTests {
```

Motivo:

- Antes esse teste subia usando o `application.properties` principal.
- Isso fazia o teste tentar conectar no PostgreSQL local.
- Agora ele usa o banco H2 em memoria configurado para testes.

### 7. Configurado Mockito para evitar erro de auto-attach

Durante a execucao da suite, surgiu uma falha relacionada ao Mockito/Byte Buddy:

```text
Could not initialize inline Byte Buddy mock maker.
It appears as if your JDK does not supply a working agent attachment mechanism.
```

Foi criado o arquivo:

```text
src/test/resources/mockito-extensions/org.mockito.plugins.MockMaker
```

Com o conteudo:

```text
mock-maker-subclass
```

Motivo:

- O ambiente estava bloqueando ou nao suportando o auto-attach usado pelo mock maker inline do Mockito.
- Como os testes atuais nao precisam mockar classes finais, o mock maker classico e suficiente.
- Isso estabiliza a execucao dos testes nesse ambiente.

## Estado Atual do Teste `FarmaciaRepositoryTest`

O teste atual valida dois comportamentos do repositorio:

1. Buscar farmacias por distrito.
2. Buscar farmacias por bairro.

Resumo da classe:

```java
@DataJpaTest(showSql = false)
@ActiveProfiles("test")
class FarmaciaRepositoryTest {

  @Autowired private FarmaciaRepository farmaciaRepository;

  @Test
  @DisplayName("Deve buscar farmacias por distrito")
  void deveBuscarFarmaciasPorDistrito() {
    // cria dados
    // consulta por distrito
    // valida que todos os resultados sao do distrito esperado
  }

  @Test
  @DisplayName("Deve buscar farmacias por bairro")
  void deveBuscarFarmaciasPorBairro() {
    // cria dados
    // consulta por bairro
    // valida que todos os resultados sao do bairro esperado
  }
}
```

## Estado Atual do Projeto

Estrutura relevante apos as correcoes:

```text
src/
  main/
    java/
      br/com/othiagob/cidadaobot/
        CidadaobotApplication.java
        farmacia/
          Farmacia.java
          FarmaciaRepository.java
    resources/
      application.properties
      db/migration/
        V1__criar_tabela_farmacias.sql

  test/
    java/
      br/com/othiagob/cidadaobot/
        CidadaobotApplicationTests.java
        farmacia/
          FarmaciaRepositoryTest.java
    resources/
      application-test.properties
      mockito-extensions/
        org.mockito.plugins.MockMaker
```

## Validacao Executada

Comando executado:

```bash
./mvnw test
```

Resultado final:

```text
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

## Observacao Sobre o Diretorio `target`

Durante a primeira execucao, o Maven falhou antes da compilacao porque havia um diretorio `target` antigo com permissao incorreta:

```text
target/classes/application.properties (Permission denied)
```

Foi identificado que o diretorio estava com dono `nobody`. Para continuar a validacao, o diretorio antigo foi renomeado para:

```text
target.stale
```

Depois disso, o Maven conseguiu criar um novo `target` corretamente e os testes passaram.

Esse diretorio antigo nao faz parte do codigo-fonte do projeto. Ele e apenas artefato de build local.

## Resultado

O problema principal foi corrigido movendo o teste para o source set correto de testes e ajustando a configuracao para rodar com banco em memoria.

O projeto agora consegue executar os testes automatizados sem depender do banco PostgreSQL local para os testes existentes.

