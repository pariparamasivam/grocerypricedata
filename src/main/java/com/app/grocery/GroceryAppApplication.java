package com.app.grocery;

import java.util.TimeZone;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;


@SpringBootApplication
@EnableJpaRepositories("com.app.grocery.repository")
@ComponentScan(basePackages = { "com.app.grocery" })
@EntityScan("com.app.grocery.entity")   
@Configuration
@OpenAPIDefinition(info = @Info(title = "Grocery Price Data API", version = "0.1", description="Grocery Price Data Service"))
public class GroceryAppApplication extends SpringBootServletInitializer {

	
	
	public static void main(String[] args) {
		SpringApplication.run(GroceryAppApplication.class, args);
	}
	
	  @Bean 
	  public ModelMapper modelMapper(){ return new ModelMapper(); }
	  
	  @Bean
	  ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
	      return builder.createXmlMapper(false)
	              // Set timezone for JSON serialization as system timezone
	              .timeZone(TimeZone.getDefault())
	              .build();
	  }
	 
	   @Bean
	    public MethodValidationPostProcessor methodValidationPostProcessor() {
	        return new MethodValidationPostProcessor();
	    }
}
