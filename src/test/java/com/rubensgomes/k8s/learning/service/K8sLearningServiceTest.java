package com.rubensgomes.k8s.learning.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rubensgomes.k8s.learning.model.K8sLearningRequest;
import com.rubensgomes.k8s.learning.model.K8sLearningResponse;

/**
 * {@link K8sLearningServiceTest} unit test class.
 *
 * @author Rubens Gomes
 */
class K8sLearningServiceTest
{
   private K8sLearningService service;

   @BeforeEach
   void setup()
   {
      service = new K8sLearningService();
   }

   @Test
   void failMsgDueToNullRequest()
   {
      K8sLearningRequest request = null;
      Assertions.assertThrows( NullPointerException.class,
                               () -> service.message( request ) );
   }

   @Test
   void ensureMsgWorks()
   {
      K8sLearningRequest request = new K8sLearningRequest();
      request.setInput( "hello world" );
      K8sLearningResponse response = service.message( request );
      Assertions.assertNotNull( response );
   }

}
