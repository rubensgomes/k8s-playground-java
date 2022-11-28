package com.rubensgomes.k8s;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.rubensgomes.k8s.service.K8sLearningService;

@ActiveProfiles( "test" )
@SpringBootTest
class K8sLearningApplicationIT
{
   @Autowired
   private K8sLearningService service;

   @Test
   void ensureApplicationStarts()
   {
      Assertions.assertNotNull( service );
   }

}
