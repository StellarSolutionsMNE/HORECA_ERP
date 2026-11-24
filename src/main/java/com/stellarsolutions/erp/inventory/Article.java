/**
 * @author Vladan Babić
 * @role Founder
 * @owner Stellar Solutions
 * @created Nov 24, 2026
 */
package com.stellarsolutions.erp.inventory;

import com.stellarsolutions.erp.company.Company;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "article")
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;

	@Column(nullable = false, length = 50)
	private String code;

	@Column(nullable = false, length = 255)
	private String name;

	@Column(name = "unit_of_measure", nullable = false, length = 30)
	private String unitOfMeasure;

	@Column(nullable = false)
	private boolean active = true;

	protected Article() {
		// Konstruktor za JPA.
	}

	public Article(Company company, String code, String name, String unitOfMeasure) {
		this.company = company;
		this.code = code;
		this.name = name;
		this.unitOfMeasure = unitOfMeasure;
	}

	public Long getId() {
		return id;
	}

	public Company getCompany() {
		return company;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public boolean isActive() {
		return active;
	}
}