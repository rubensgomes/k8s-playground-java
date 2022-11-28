package com.rubensgomes.k8s.config;

import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * {@link K8sLearningConfiguration} is an Spring Boot application configuration
 * class.
 *
 * @author Rubens Gomes
 */
@Slf4j
@Configuration
class K8sLearningConfiguration
{
   K8sLearningConfiguration()
   {
      log.trace( "constructed" );
   }
}
