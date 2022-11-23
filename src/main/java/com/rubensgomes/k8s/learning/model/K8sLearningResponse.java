package com.rubensgomes.k8s.learning.model;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * {@link K8sLearningResponse} is a simple response used in HTTP POST responses.
 *
 * @author Gomes, Rubens <Rubens.S.Gomes@gmail.com>
 */
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class K8sLearningResponse
{
   @NotBlank( message = "output cannot be null or blank" )
   private String output;
}
