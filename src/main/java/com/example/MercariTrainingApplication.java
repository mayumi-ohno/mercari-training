package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
// 外部tomcatデプロイ用にサーブレットとして処理できるようにするため継承
public class MercariTrainingApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MercariTrainingApplication.class, args);
	}

// 外部tomcatデプロイ用にサーブレットとして処理できるようにするためOverride
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MercariTrainingApplication.class);
	}

}
