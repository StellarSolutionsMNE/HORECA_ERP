/**
 * @author Vladan Babić
 * @role Founder
 * @owner Stellar Solutions
 * @created Nov 11, 2026
 */
package com.stellarsolutions.erp.company;

public class CompanyAlreadyExistsException extends RuntimeException {

    public CompanyAlreadyExistsException() {
        super("Firma sa ovom državom i poreskim identifikatorom već postoji.");
    }
}
