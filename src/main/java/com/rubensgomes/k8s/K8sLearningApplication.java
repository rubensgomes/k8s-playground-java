package com.rubensgomes.k8s;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class K8sLearningApplication
{
   K8sLearningApplication()
   {
      log.trace( "constructed" );
   }

   public static void main( String[] args )
   {
      log.debug( "Starting the [{}] application",
                 K8sLearningApplication.class.getName() );
      SpringApplication.run( K8sLearningApplication.class );
   }

}
