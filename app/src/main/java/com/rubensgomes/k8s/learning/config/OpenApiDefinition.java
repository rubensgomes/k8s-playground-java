package com.rubensgomes.k8s.learning.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.ServerVariable;
import lombok.extern.slf4j.Slf4j;

/**
 * {@link OpenApiDefinition} is used the microservice Swagger page to populate
 * the UI with description, server URL end point address, and server attribute
 * choices
 *
 * @author Rubens Gomes
 */
//@formatter:off
@OpenAPIDefinition
(
   info = 
     @Info
     ( 
        title       = "Kubernetes Learning Micro Service",
        description = "A simple application used to be deployed in K8s",
        contact     = @Contact
                      (
                         name  = "Rubens Gomes"  ,
                         url   = "https://rubensgomes.com" ,
                         email = "rubens.s.gomes@gmail.com"
                      ),
        version     = "0.0.1"
     ),
   
   servers =
   {
     @Server
     (
         url         = "http://localhost:8080",
         description = "K8s Learning Microservicer"
     )
   }
)
//@formatter:on
@Slf4j
class OpenApiDefinition
{
   // dummy type used to define an OpenAPIDefinition
   OpenApiDefinition()
   {
      log.trace( "constructed" );
   }

}
