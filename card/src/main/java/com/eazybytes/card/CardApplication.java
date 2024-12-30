package com.eazybytes.card;

import com.eazybytes.card.dto.CardContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@OpenAPIDefinition(
		info = @Info(
				title = "Card Microservice REST API Documentation",
				description = "EazyBank Card Microservice REST API",
				version = "Version 0.0.1",
				contact = @Contact(
						name = "Udoka Excellence",
						email = "uexcel@gmail.com",
						url = "https://www.eazybank.com/contact"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.eazybank.com/license"
				),
				termsOfService = "https://www.eazybank.com/terms&conditions"
		),
		externalDocs = @ExternalDocumentation(
				description = "EazyBank REST API Documentation",
				url = "https://www.eazybank.com/api/info"
		)
)


@SpringBootApplication
@EnableConfigurationProperties(value = CardContactInfoDto.class)
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
public class CardApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardApplication.class, args);
	}

}
