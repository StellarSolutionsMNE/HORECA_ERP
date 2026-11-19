/**
 * @author Vladan Babić
 * @role Founder
 * @owner Stellar Solutions
 * @created Nov 11, 2026
 */
package com.stellarsolutions.erp.company;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.stellarsolutions.erp.company.dto.CreateCompanyRequest;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;

class CompanyServiceTest {

    private static final ValidatorFactory VALIDATOR_FACTORY =
        Validation.buildDefaultValidatorFactory();

    private CompanyRepository companyRepository;
    private CompanyService companyService;

    @BeforeEach
    void setUp() {
        companyRepository = mock(CompanyRepository.class);

        companyService = new CompanyService(
            companyRepository,
            VALIDATOR_FACTORY.getValidator()
        );
    }

    @AfterAll
    static void closeValidatorFactory() {
        VALIDATOR_FACTORY.close();
    }

    @Test
    void createsCompanyWithNormalizedValues() {
        var request = request("  Stellar Solutions DOO  ", "  01234567  ");

        when(companyRepository.save(any(Company.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        var response = companyService.create(request);

        var captor = ArgumentCaptor.forClass(Company.class);

        verify(companyRepository).save(captor.capture());

        Company savedCompany = captor.getValue();

        assertAll(
            () -> assertEquals(
                "Stellar Solutions DOO",
                savedCompany.getLegalName()
            ),
            () -> assertEquals("01234567", savedCompany.getTaxId()),
            () -> assertEquals("ME", savedCompany.getCountryCode()),
            () -> assertNull(savedCompany.getAddressLine()),
            () -> assertEquals("Berane", savedCompany.getCity()),
            () -> assertTrue(response.active())
        );

        verify(companyRepository)
            .existsByCountryCodeAndTaxId("ME", "01234567");
    }

    @Test
    void rejectsDuplicateCompany() {
        when(companyRepository.existsByCountryCodeAndTaxId(
            "ME",
            "01234567"
        )).thenReturn(true);

        assertThrows(
            CompanyAlreadyExistsException.class,
            () -> companyService.create(
                request("Stellar Solutions DOO", "01234567")
            )
        );

        verify(companyRepository, never()).save(any(Company.class));
    }

    @Test
    void rejectsBlankLegalNameBeforeAccessingRepository() {
        assertThrows(
            ConstraintViolationException.class,
            () -> companyService.create(request("   ", "01234567"))
        );

        verifyNoInteractions(companyRepository);
    }

    private static CreateCompanyRequest request(
        String legalName,
        String taxId
    ) {
        return new CreateCompanyRequest(
            legalName,
            taxId,
            "ME",
            "   ",
            "  Berane  ",
            null,
            null
        );
    }
}