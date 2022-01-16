package com.JNUHealthSupervisor.ucdas;

import org.springframework.boot.SpringApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableSwagger2
@EnableScheduling
@SpringBootApplication
@EnableRedisHttpSession
public class UCDASApplication {

	public static void main(String[] args) {
		SpringApplication.run(UCDASApplication.class, args);
	}

}
