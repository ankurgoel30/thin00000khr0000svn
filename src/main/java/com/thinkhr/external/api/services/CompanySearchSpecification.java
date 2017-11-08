package com.thinkhr.external.api.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.thinkhr.external.api.db.entities.Company;

public class CompanySearchSpecification implements Specification<Company>{
	
	private String searchSpec ;
	
	private static final List<String> searchColumns = new ArrayList<String>();
	
	static {
		searchColumns.add("searchHelp");
		searchColumns.add("companyName");
		searchColumns.add("companyPhone");
		searchColumns.add("website");
	}
	
	public CompanySearchSpecification(String searchSpec) {
		super();
		this.searchSpec =  searchSpec;
	}

	@Override
	public Predicate toPredicate(Root<Company> from, CriteriaQuery<?> criteria, CriteriaBuilder criteriaBuilder) {
		Predicate p = criteriaBuilder.disjunction();
		if(searchSpec != null && searchSpec.trim() != "") {
			searchColumns.stream().forEach(column -> p.getExpressions().add(criteriaBuilder.like(from.get(column), "%" + searchSpec +"%")));
		}
		return p;
	}

}
