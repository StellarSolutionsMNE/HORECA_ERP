/**
 * @author Vladan Babić
 * @role Founder
 * @owner Stellar Solutions
 * @created Nov 25, 2026
 */
package com.stellarsolutions.erp.inventory;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "stock_movement")
public class StockMovement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "company_id", nullable = false, updatable = false)
	private Long companyId;

	@Column(name = "article_id", nullable = false, updatable = false)
	private Long articleId;

	@Column(name = "operation_id", nullable = false, updatable = false)
	private UUID operationId;

	@Column(name = "movement_type", nullable = false, length = 20, updatable = false)
	private String movementType;

	@Column(name = "quantity_delta", nullable = false, precision = 18, scale = 3, updatable = false)
	private BigDecimal quantityDelta;

	@Column(name = "recorded_at", nullable = false, updatable = false)
	private OffsetDateTime recordedAt;

	protected StockMovement() {
		// Konstruktor za JPA.
	}

	public static StockMovement receipt(
			long companyId, long articleId, UUID operationId, BigDecimal quantity) {
		StockMovement movement = new StockMovement();
		movement.companyId = companyId;
		movement.articleId = articleId;
		movement.operationId = operationId;
		movement.movementType = "RECEIPT";
		movement.quantityDelta = quantity;
		movement.recordedAt = OffsetDateTime.now(ZoneOffset.UTC);
		return movement;
	}

	public Long getId() {
		return id;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public Long getArticleId() {
		return articleId;
	}

	public UUID getOperationId() {
		return operationId;
	}

	public String getMovementType() {
		return movementType;
	}

	public BigDecimal getQuantityDelta() {
		return quantityDelta;
	}

	public OffsetDateTime getRecordedAt() {
		return recordedAt;
	}
}