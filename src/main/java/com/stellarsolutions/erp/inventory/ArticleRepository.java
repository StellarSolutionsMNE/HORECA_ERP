/**
 * @author Vladan Babić
 * @role Founder
 * @owner Stellar Solutions
 * @created Nov 24, 2026
 */
package com.stellarsolutions.erp.inventory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

	boolean existsByCompany_IdAndCode(Long companyId, String code);

	List<Article> findAllByCompany_IdOrderByCodeAsc(Long companyId);
}
