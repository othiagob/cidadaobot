package br.com.othiagob.cidadaobot.configuracao;

import java.time.Duration;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

@Configuration
@EnableCaching
public class CacheConfig {

  private static final Duration TTL_CACHE_PLANTOES = Duration.ofSeconds(60);

  @Bean
  public RedisCacheManagerBuilderCustomizer configurarCacheRedis() {
    RedisCacheConfiguration configuracaoPadrao =
        RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(TTL_CACHE_PLANTOES)
            .disableCachingNullValues()
            .prefixCacheNameWith("cidadaobot::");

    return builder ->
        builder
            .cacheDefaults(configuracaoPadrao)
            .withCacheConfiguration("plantoesPorData", configuracaoPadrao)
            .withCacheConfiguration("plantoesAtuais", configuracaoPadrao);
  }
}
