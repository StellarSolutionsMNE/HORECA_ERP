/**
 * @author Vladan Babić
 * @role Founder
 * @owner Stellar Solutions
 * @created Nov 25, 2026
 */
package com.stellarsolutions.erp.inventory.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ReceiveStockRequest(
		@NotNull @Positive Long articleId,
		@NotNull @Positive @Digits(integer = 15, fraction = 3) BigDecimal quantity
) {
}