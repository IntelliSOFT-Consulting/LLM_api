package com.intellisoft.llm;

import com.intellisoft.llm.configuration.RsaKeyProps;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProps.class)
public class LlmApplication {

	public static void main(String[] args) {
		SpringApplication.run(LlmApplication.class, args);
	}

}
