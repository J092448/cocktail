package com.ontherocks.tlqkf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ontherocks.tlqkf.repository") // ✅ 추가
public class TlqkfApplication {
	public static void main(String[] args) {
		SpringApplication.run(TlqkfApplication.class, args);
	}
}


