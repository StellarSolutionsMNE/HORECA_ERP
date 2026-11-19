package com.stellarsolutions.erp.company.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateCompanyRequest(

    @NotBlank
    @Size(max = 255)
    String legalName,

    @NotBlank
    @Size(max = 50)
    String taxId,

    @NotBlank
    @Pattern(regexp = "^[A-Z]{2}$")
    String countryCode,

    @Size(max = 255)
    String addressLine,

    @Size(max = 100)
    String city,

    @Email
    @Size(max = 254)
    String email,

    @Size(max = 50)
    String phone

) {
}