/**
 * @author Vladan Babić
 * @role Founder
 * @owner Stellar Solutions
 * @created Nov 11, 2026
 */
package com.stellarsolutions.erp.company;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stellarsolutions.erp.company.dto.CompanyResponse;
import com.stellarsolutions.erp.company.dto.CreateCompanyRequest;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

@Service
public class CompanyService {

	private final CompanyRepository companyRepository;
	private final Validator validator;

	public CompanyService(CompanyRepository companyRepository, Validator validator) {
		this.companyRepository = companyRepository;
		this.validator = validator;
	}

	@Transactional
	public CompanyResponse create(CreateCompanyRequest request) {
		Objects.requireNonNull(request, "Zahtjev je obavezan.");

		var violations = validator.validate(request);

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(violations);
		}

		String legalName = request.legalName().strip();
		String taxId = request.taxId().strip();
		String countryCode = request.countryCode();

		if (companyRepository.existsByCountryCodeAndTaxId(countryCode, taxId)) {
			throw new CompanyAlreadyExistsException();
		}

		Company company = new Company(legalName, taxId, countryCode);

		company.setAddressLine(normalizeOptional(request.addressLine()));
		company.setCity(normalizeOptional(request.city()));
		company.setEmail(normalizeOptional(request.email()));
		company.setPhone(normalizeOptional(request.phone()));

		Company savedCompany = companyRepository.save(company);

		return CompanyResponse.from(savedCompany);
	}

	private static String normalizeOptional(String value) {
		if (value == null || value.isBlank()) {
			return null;
		}

		return value.strip();
	}
}
