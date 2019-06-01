package com.truck.food.config;

import static springfox.documentation.schema.AlternateTypeRules.newRule;
import static com.truck.food.constant.CommonConstant.LICENSE_ENDPOINT;

import java.lang.reflect.WildcardType;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Class for including Swagger library This library helps in documenting Your
 * Application Rest Services Swagger provides UI that contains description of
 * your Application Rest Service
 * 
 * @author mmt3262
 *
 */
@Configuration
@EnableSwagger2
//@EnableAutoConfiguration
public class SwaggerConfig {                                    
    
	@Bean
    public Docket api() { 
    	TypeResolver typeResolver=new TypeResolver();
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                               
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())
          .build().apiInfo(apiInfo())
          .genericModelSubstitutes(ResponseEntity.class,DeferredResult.class)
          .alternateTypeRules(
                  newRule(typeResolver.resolve(DeferredResult.class,
                      typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                      typeResolver.resolve(WildcardType.class)))
              .useDefaultResponseMessages(false);
    }   	

	/**
	 * Contains your REST API Information
	 * @return
	 */
	private ApiInfo apiInfo() {
        @SuppressWarnings("deprecation")
		ApiInfo apiInfo = new ApiInfo(
          "Food-Truck-Smaple Application APIs",
          "Provides sample responses for default requests",
          "v1",
          "",
          "shafeeequl@gmail.com",
          "License of API",
          "/food-truck-sample" + LICENSE_ENDPOINT);
		return apiInfo;
	}

	@Bean
	@Description("Validator API to validate Java Pojo")
	public Validator getValidator() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		return factory.getValidator();
	}
	
}