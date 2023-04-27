package com.project.fri.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * packageName    : com.project.fri.config fileName       : JasyptConfig date           : 2023-04-27
 * description    :
 */
@Configuration
public class JasyptConfig {

  @Value("${jasypt.encryptor.password}")
  private String PASSWORD;

  @Bean(name = "jasyptStringEncryptor")
  public StringEncryptor stringEncryptor() {

    PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
    SimpleStringPBEConfig config = new SimpleStringPBEConfig();
    config.setPassword(PASSWORD); // 암호화할 때 사용하는 키
    config.setAlgorithm("PBEWithMD5AndDES"); // 암호화 알고리즘
    config.setKeyObtentionIterations("1000"); // 반복할 해싱 회수
    config.setPoolSize("1"); // 인스턴스 pool
    config.setProviderName("SunJCE");
    config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator"); // salt 생성 클래스
    config.setStringOutputType("base64"); //인코딩 방식
    encryptor.setConfig(config);
    return encryptor;
  }
}
