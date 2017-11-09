package com.thinkhr.external.api.services.util;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.thinkhr.external.api.db.entities.SearchableEntity;

public class EntitySearchSpecification<T extends SearchableEntity> implements Specification<T>{
	
	private String searchSpec ;
	private T t ;
	
	public EntitySearchSpecification(String searchSpec,T entity) {
		super();
		this.searchSpec =  searchSpec;
		this.t =  entity;
	}

	@Override
	public Predicate toPredicate(Root<T> from, CriteriaQuery<?> criteria, CriteriaBuilder criteriaBuilder) {
		Predicate p = criteriaBuilder.disjunction();
		if(searchSpec != null && searchSpec.trim() != "") {
			List<String> searchColumns =  t.getSearchFields();
			//TODO : handle when searchColumns is null or emptyList
			searchColumns.stream().forEach(column -> p.getExpressions().add(criteriaBuilder.like(from.get(column), "%" + searchSpec +"%")));
		}
		return p;
	}

}
