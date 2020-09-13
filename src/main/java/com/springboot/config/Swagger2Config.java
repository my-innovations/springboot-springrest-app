package com.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {
	
	//http://localhost:8081/v2/api-docs
	//http://localhost:8081/swagger-ui.html

	@Bean
	public Docket userRestApi() {
	
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("public-api")
				.select()
				.apis(RequestHandlerSelectors.any())
				//.apis(RequestHandlerSelectors.basePackage("com.springboot.restcontroller"))
				.paths(PathSelectors.any())
				//.paths(regex("/api/rest/*"))
				//.paths(postPaths())
				.build()
				.apiInfo(getApiInfo());
		
		//OR
		
		//return new Docket(DocumentationType.SWAGGER_2)
			//	.apiInfo(getApiInfo())
				//.produces(new HashSet<String>(Arrays.asList("application/json","application/xml")))
				//.consumes(new HashSet<String>(Arrays.asList("application/json","application/xml")));
		
		//OR
		
		/*return new Docket(DocumentationType.SWAGGER_2)
	            .select()
	            .apis(RequestHandlerSelectors.basePackage("com.springboot.controller"))
	            .paths(regex("/v1.*"))
	            .build();*/
	}
	
	private ApiInfo getApiInfo() {
		return new ApiInfoBuilder()
				.title("User Rest API")
				.description("User Rest API reference for developers")
				.termsOfServiceUrl("https://google.com")
				.contact(new Contact("Punyasmruti Nayak","https://google.com","punyasmruti@gmail.com"))
				.license("apache 2.0 license")
				.licenseUrl("www.apache.com")
				.version("1.0")
				.build();
	}

	/*@SuppressWarnings({ "unchecked", "unused" })
	private Predicate<String> postPaths() {
		return or(
				regex("/api/posts.*"), 
				regex("/api/rest.*"),
				regex("/v1.*")
				);
	}*/
}



