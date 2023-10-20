package com.maypink.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class JdbcApplication {

	public static void main(String[] args) {

		SpringApplication.run(JdbcApplication.class, args);
	}

}
