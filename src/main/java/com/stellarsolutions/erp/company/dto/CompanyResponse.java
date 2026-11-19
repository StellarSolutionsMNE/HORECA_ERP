package com.stellarsolutions.erp.company.dto;

import com.stellarsolutions.erp.company.Company;


public record CompanyResponse(
	    Long id,
	    String legalName,
	    String taxId,
	    String countryCode,
	    String addressLine,
	    String city,
	    String email,
	    String phone,
	    boolean active
	) {
	    public static CompanyResponse from(Company company) {
	        return new CompanyResponse(
	            company.getId(),
	            company.getLegalName(),
	            company.getTaxId(),
	            company.getCountryCode(),
	            company.getAddressLine(),
	            company.getCity(),
	            company.getEmail(),
	            company.getPhone(),
	            company.isActive()
	        );
	    }
	}