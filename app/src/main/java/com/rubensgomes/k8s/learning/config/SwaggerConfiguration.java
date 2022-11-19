package com.rubensgomes.k8s.learning.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

/**
 * Registers a {@link ViewControllerRegistry} to render the Swagger page.
 * 
 * @author Rubens Gomes
 */
@Slf4j
@Configuration
class SwaggerConfiguration implements WebMvcConfigurer
{
   private static final String SWAGGER_UI_INDEX_PAGE = "/swagger-ui.html";

   SwaggerConfiguration()
   {
      log.trace( "constructed" );
   }

   @Override
   public void addViewControllers( final ViewControllerRegistry registry )
   {
      log.debug( "Registering Swagger-UI index page [{}]",
                 SWAGGER_UI_INDEX_PAGE );
      registry.addViewController( "/" )
              .setViewName( "redirect:" + SWAGGER_UI_INDEX_PAGE );
   }

}
