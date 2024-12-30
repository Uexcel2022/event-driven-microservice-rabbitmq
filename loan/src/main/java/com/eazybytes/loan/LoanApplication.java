package com.eazybytes.loan;

import com.eazybytes.loan.dto.LoanContactInfoDto;
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
				title = "CRUD Microservice API",
				description = "Loan Microservice CRUD Operations in EazyBank",
				version = "Version 0.0.1",
				contact = @Contact(
						name = "EazyBank Customer Care",
						email = "customercare@eazybank.com",
						url = "https://www.eazybank.com/customer/care"
				),
				license = @License(
						name = "Licence Apache 2.0",
						url = "https://www.eazybank.com/license"
				),
				termsOfService = "https://www.eazybank.com/terms&conditions"


		),
		externalDocs = @ExternalDocumentation(
				description = "External APIs Documentation",
				url = "https://www.eazybank.com/api/docs"

		)

)

@SpringBootApplication
@EnableConfigurationProperties(value = LoanContactInfoDto.class)
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
public class LoanApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanApplication.class, args);
	}

}
