package br.com.othiagob.cidadaobot.configuracao;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("CidadaoBot API - Farmácias de Plantão")
                .description(
                    """
                            API REST desenvolvida em Spring Boot para consulta
                            de farmácias de plantão em Ji-Paraná RO.

                            Projeto construído para estudo avançado de backend, arquitetura REST, Java e Spring Boot.
                    """)
                .version("v1.0.0")
                .contact(
                    new Contact().name("Thiago Borghardt").url("https://github.com/othiagob")));
  }
}
