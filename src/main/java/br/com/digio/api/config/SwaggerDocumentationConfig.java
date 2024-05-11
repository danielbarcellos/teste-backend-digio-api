package br.com.digio.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerDocumentationConfig {

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("DIGIO - API")
            .description("API relativa aos serviços disponiveis do DIGIO-api.")
            .license("")
            .licenseUrl("http://unlicense.org")
            .termsOfServiceUrl("")
            .version("v1.0.0.0")
            .contact(new Contact("Renan Pessoa","", "renan.pessoa@digio.com.br"))
            .build();
    }

    @Bean
    public Docket customImplementation(){
        return new Docket(DocumentationType.SWAGGER_2)
        		.select()
                .apis(RequestHandlerSelectors.basePackage("br.com.digio.api.controller"))
                .build()
            .apiInfo(apiInfo());
    }

}
