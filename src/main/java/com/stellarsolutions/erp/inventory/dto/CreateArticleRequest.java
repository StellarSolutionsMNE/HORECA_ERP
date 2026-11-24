/**
 * @author Vladan Babić
 * @role Founder
 * @owner Stellar Solutions
 * @created Nov 24, 2026
 */
package com.stellarsolutions.erp.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateArticleRequest(
		@NotBlank @Size(max = 50)
		String code,

		@NotBlank @Size(max = 255)
		String name,

		@NotBlank @Size(max = 30)
		String unitOfMeasure)
{
}