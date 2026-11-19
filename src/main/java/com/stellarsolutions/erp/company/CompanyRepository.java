package com.stellarsolutions.erp.company;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    boolean existsByCountryCodeAndTaxId(
        String countryCode,
        String taxId
    );
}