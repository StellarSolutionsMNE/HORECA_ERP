/**
 * @author Vladan Babić
 * @role Founder
 * @owner Stellar Solutions
 * @created Nov 24, 2026
 */
package com.stellarsolutions.erp.inventory;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.stellarsolutions.erp.company.Company;
import com.stellarsolutions.erp.company.CompanyRepository;
import com.stellarsolutions.erp.inventory.dto.ArticleResponse;
import com.stellarsolutions.erp.inventory.dto.CreateArticleRequest;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

@Service
public class ArticleService {

	private final ArticleRepository articleRepository;
	private final CompanyRepository companyRepository;
	private final Validator validator;

	public ArticleService(
			ArticleRepository articleRepository,
			CompanyRepository companyRepository,
			Validator validator) {
		this.articleRepository = articleRepository;
		this.companyRepository = companyRepository;
		this.validator = validator;
	}

	@Transactional
	public ArticleResponse create(long companyId, CreateArticleRequest request) {
		Objects.requireNonNull(request, "Zahtjev je obavezan.");

		var violations = validator.validate(request);
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(violations);
		}

		Company company = requireActiveCompany(companyId);

		String code = request.code().strip();

		if (articleRepository.existsByCompany_IdAndCode(companyId, code)) {
			throw new ResponseStatusException(
					HttpStatus.CONFLICT, "Šifra artikla već postoji u ovoj firmi.");
		}

		Article article = new Article(
				company,
				code,
				request.name().strip(),
				request.unitOfMeasure().strip());

		Article savedArticle = articleRepository.save(article);
		return ArticleResponse.from(savedArticle);
	}

	@Transactional(readOnly = true)
	public List<ArticleResponse> listByCompany(long companyId) {
		requireActiveCompany(companyId);

		return articleRepository.findAllByCompany_IdOrderByCodeAsc(companyId)
				.stream()
				.map(ArticleResponse::from)
				.toList();
	}

	private Company requireActiveCompany(long companyId) {
		return companyRepository.findById(companyId)
				.filter(Company::isActive)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND, "Aktivna firma nije pronađena."));
	}
}
