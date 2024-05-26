package com.found_404.funcomember.global.config;

import static com.found_404.funcomember.global.exception.RedisErrorCode.*;
import static com.found_404.funcomember.global.type.RedisDatabaseType.*;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.found_404.funcomember.favoritecoin.dto.FavoriteCoinInfo;
import com.found_404.funcomember.global.exception.RedisException;
import com.found_404.funcomember.member.dto.RankDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableTransactionManagement
public class RedisConfig {

	@Value("${spring.data.redis.host}")
	private String host;

	@Value("${spring.data.redis.password}")
	private String password;

	@Value("${spring.data.redis.port}")
	private int port;

	private LettuceConnectionFactory createLettuceConnectionFactory(int database) {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName(host);
		redisStandaloneConfiguration.setPort(port);
		redisStandaloneConfiguration.setPassword(password);
		redisStandaloneConfiguration.setDatabase(database);

		LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
		lettuceConnectionFactory.afterPropertiesSet();

		return lettuceConnectionFactory;
	}

	// redis 서버 꺼졌는지 확인
	public void isRedisAlive(RedisTemplate<String, Object> redisTemplate) {
		try {
			// Redis 서버에 ping 요청을 보내고 응답을 확인
			String pong = Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().ping();
			log.info("===================== Redis Sever Operates well =====================", pong);
		} catch (Exception e) {
			throw new RedisException(REDIS_SEVER_OFF);
		}
	}

	// 관심있는 코인 템플릿
	@Bean
	public RedisTemplate<String, Object> favoriteCoinRedisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(createLettuceConnectionFactory(FAVORITE_COIN.ordinal()));
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		// Value 직렬화를 위한 ObjectMapper 설정
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.registerModule(new Jdk8Module());
		objectMapper.registerModule(new ParameterNamesModule());
		// Value 직렬화 설정
		Jackson2JsonRedisSerializer<FavoriteCoinInfo> jackson2JsonRedisSerializer =
			new Jackson2JsonRedisSerializer<>(objectMapper, FavoriteCoinInfo.class);
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);

		isRedisAlive(redisTemplate);

		return redisTemplate;
	}

	// 관심코인 zset 템플릿
	@Bean
	public RedisTemplate<String, Object> favoriteCoinZSetRedisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(createLettuceConnectionFactory(FAVORITE_COIN.ordinal()));
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer()); // ZSet 값에 대한 직렬화

		isRedisAlive(redisTemplate);

		return redisTemplate;
	}

	// 랭킹 zset 템플릿
	@Bean
	public RedisTemplate<String, Object> rankZSetRedisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(createLettuceConnectionFactory(RANKING.ordinal()));
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		// Value 직렬화를 위한 ObjectMapper 설정
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.registerModule(new Jdk8Module());
		objectMapper.registerModule(new ParameterNamesModule());
		// Value 직렬화 설정
		Jackson2JsonRedisSerializer<RankDto> jackson2JsonRedisSerializer =
			new Jackson2JsonRedisSerializer<>(objectMapper, RankDto.class);
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);

		isRedisAlive(redisTemplate);

		return redisTemplate;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new JpaTransactionManager();
	}

}