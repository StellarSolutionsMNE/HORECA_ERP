/**
 * @author Vladan Babić
 * @role Founder
 * @owner Stellar Solutions
 * @created Nov 24, 2026
 */
package com.stellarsolutions.erp.inventory.dto;

import com.stellarsolutions.erp.inventory.Article;

public record ArticleResponse(
		Long id,
		String code,
		String name,
		String unitOfMeasure,
		boolean active
) {
	public static ArticleResponse from(Article article) {
		return new ArticleResponse(
				article.getId(),
				article.getCode(),
				article.getName(),
				article.getUnitOfMeasure(),
				article.isActive());
	}
}