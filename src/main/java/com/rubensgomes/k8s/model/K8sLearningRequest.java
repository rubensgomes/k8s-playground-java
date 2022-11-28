package com.rubensgomes.k8s.model;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * {@link K8sLearningRequest} is a simple request used in HTTP POST request.
 *
 * @author Gomes, Rubens <Rubens.S.Gomes@gmail.com>
 */
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class K8sLearningRequest
{
   @NotBlank( message = "input cannot be null or blank" )
   private String input;
}
