package com.rubensgomes.k8s.learning.service;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import com.rubensgomes.k8s.learning.model.K8sLearningRequest;
import com.rubensgomes.k8s.learning.model.K8sLearningResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * {@link K8sLearningService} is a service layer class used by the K8S learning
 * application.
 * 
 * @author Rubens Gomes
 */
@Slf4j
@Service
public class K8sLearningService
{
   K8sLearningService()
   {
      log.trace( "constructed" );
   }

   /**
    * A dummy method to exercise the service layer.
    *
    * @param request the non-null {@link K8sLearningRequest} request.
    * @return a non-null {@link K8sLearningResponse} response.
    */
   public K8sLearningResponse message( final K8sLearningRequest request )
   {
      Validate.notNull( request,
                        "request cannot be null" );
      K8sLearningResponse response = new K8sLearningResponse();
      response.setOutput( request.getInput() );
      return response;
   }

}
