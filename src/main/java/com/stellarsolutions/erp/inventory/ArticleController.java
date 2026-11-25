/**
 * @author Vladan Babić
 * @role Founder
 * @owner Stellar Solutions
 * @created Nov 25, 2026
 */
package com.stellarsolutions.erp.inventory;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.stellarsolutions.erp.inventory.dto.ArticleResponse;
import com.stellarsolutions.erp.inventory.dto.CreateArticleRequest;

import jakarta.validation.Valid;

@RestController
@Profile("inventory-local")
@ConditionalOnProperty(name = "server.address", havingValue = "127.0.0.1")
@RequestMapping("/api/companies/{companyId}/articles")
public class ArticleController {

	private final ArticleService articleService;

	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ArticleResponse create(
			@PathVariable("companyId") long companyId,
			@Valid @RequestBody CreateArticleRequest request) {
		return articleService.create(companyId, request);
	}

	@GetMapping
	public List<ArticleResponse> listByCompany(
			@PathVariable("companyId") long companyId) {
		return articleService.listByCompany(companyId);
	}
}
