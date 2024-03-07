package com.xseedai.jobcreation;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;


@OpenAPIDefinition(
		info=@Info(
				title="RECRUITER MICRO-SERVICE REST APIs",
				description = "Job Creation And Listing REST APIs Documentation",
				version = "v1.0",
				contact=@Contact(
						name = "Prajwal,Atharva,Roshan,Khusbu",
						email="	xseedai@tcognition.com"
						
						),		
				license= @License(
						name="tCognition",
						url="https://tcognition.com/"
						
						
						)
				
				),
				externalDocs=@ExternalDocumentation(
						description="MicroServices Overview and Architecture",
						url="https://docs.google.com/document/d/1tYMCRNRIOJeK01Ynx88bq6Iw7iVLECXG/edit?usp=drive_web&ouid=118309295115974221063&rtpof=true"
						
						)

		
		)
@SpringBootApplication
public class Application {
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
