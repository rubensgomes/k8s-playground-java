package com.rubensgomes.k8s.web.controller;

import java.net.URI;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.rubensgomes.k8s.model.K8sLearningResponse;
import com.rubensgomes.k8s.service.K8sLearningService;
import com.rubensgomes.k8s.web.controller.K8sLearningRestController;

/**
 * {@link K8sLearningRestController} unit test class.
 *
 * @author Rubens Gomes
 */
@ActiveProfiles( "test" )
@WebMvcTest( controllers = { K8sLearningRestController.class } )
class K8sLearningRestControllerTest
{
   @LocalServerPort
   private String port;

   @MockBean
   private K8sLearningService service;

   @Autowired
   private MockMvc mockMvc;

   @BeforeEach
   void setup()
   {
      MockitoAnnotations.initMocks( this );
   }

   @Test
   void ensureMsgReturnOkStatus()
      throws Exception
   {
      K8sLearningResponse response = new K8sLearningResponse();
      response.setOutput( "testing" );
      Mockito.when( service.message( Mockito.any() ) )
             .thenReturn( response );
      String json = "{ \"msg\": \"test\" }";
      URI uri = new URI( "http://localhost:" +
                         port +
                         K8sLearningRestController.OPERATION );

      //@formatter:off
      MockHttpServletResponse servletResponse =
            mockMvc.perform( MockMvcRequestBuilders.post(uri)
                                                   .accept( MediaType.APPLICATION_JSON )
                                                   .content( json )
                                                   .contentType( MediaType.APPLICATION_JSON ))
                   .andReturn()
                   .getResponse();
      //@formatter:on

      Assertions.assertEquals( HttpStatus.OK.value(),
                               servletResponse.getStatus() );
   }

}
