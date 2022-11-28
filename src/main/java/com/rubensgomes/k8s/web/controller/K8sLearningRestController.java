package com.rubensgomes.k8s.web.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rubensgomes.k8s.model.K8sLearningRequest;
import com.rubensgomes.k8s.model.K8sLearningResponse;
import com.rubensgomes.k8s.service.K8sLearningService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * {@link K8sLearningRestController} is a RESTful API to provide an entry point
 * to the application.
 *
 * @author Gomes, Rubens <Rubens.S.Gomes@gmail.com>
 */
@Slf4j
@Validated
@RestController
class K8sLearningRestController
{
   static final String OPERATION = "/api/k8slearning";

   private final K8sLearningService service;

   K8sLearningRestController( final K8sLearningService service )
   {
      this.service = service;
      log.trace( "constructe" );
   }

   /**
    * The msg operation.
    *
    * @param request the non-null {@link K8sLearningRequest}
    * @return the non-null {@link K8sLearningResponse}
    */
   //@formatter:off
   @Tag
   (
      name        = "msg",
      description = "a dummy msg operation."
   )
   @PostMapping
   (
      path     = { OPERATION                        },
      consumes = { MediaType.APPLICATION_JSON_VALUE },
      produces = { MediaType.APPLICATION_JSON_VALUE }
   )
   @Operation
   (
      summary     = "Dummy msg operation to exercise controller and service",
      operationId = "msg",
      description = "Simply returns the same message back to consumer."
   )
   //@formatter:on
   @ResponseStatus( code = HttpStatus.OK )
   public ResponseEntity< K8sLearningResponse >
          msg( @Valid @RequestBody final K8sLearningRequest request )
   {
      log.trace( "Processing msg" );
      K8sLearningResponse response = service.message( request );
      return new ResponseEntity<>( response,
                                   HttpStatus.OK );
   }

}
