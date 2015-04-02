package com.github.tx.archetype.modules.core.jpa.criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * 查询条件接口
 * 
 * @author tangx
 * @since 2014年4月2日
 */
public interface Criterion {

	public enum Operator {
		EQ, NE, LIKE, GT, LT, GTE, LTE, AND, OR
	}

	/**
	 * 构建Predicate
	 * 
	 * @param root
	 * @param query
	 * @param builder
	 * @return
	 */
	public Predicate buildPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder);
}
