/**
 * @author Vladan Babić
 * @role Founder
 * @owner Stellar Solutions
 * @created Nov 25, 2026
 */
package com.stellarsolutions.erp.inventory;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

	Optional<StockMovement> findByCompanyIdAndOperationId(
			Long companyId, UUID operationId);
}
