package com.teamminari.gongjang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class GongjangBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GongjangBackendApplication.class, args);
	}

}
