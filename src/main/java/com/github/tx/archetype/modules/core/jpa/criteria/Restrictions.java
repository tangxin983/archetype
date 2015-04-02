package com.github.tx.archetype.modules.core.jpa.criteria;

import com.github.tx.archetype.modules.core.jpa.criteria.Criterion.Operator;

/**
 * 查询条件构造器
 * 
 * @author tangx
 * @since 2014年4月2日
 */
public class Restrictions {

	/**
	 * 等于
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public static SimpleExpression eq(String fieldName, Object value) {
		if (value == null) {
			return null;
		}
		return new SimpleExpression(fieldName, value, Operator.EQ);
	}

	/**
	 * 不等于
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public static SimpleExpression ne(String fieldName, Object value) {
		if (value == null) {
			return null;
		}
		return new SimpleExpression(fieldName, value, Operator.NE);
	}

	/**
	 * 模糊匹配
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public static SimpleExpression like(String fieldName, Object value) {
		if (value == null) {
			return null;
		}
		return new SimpleExpression(fieldName, value, Operator.LIKE);
	}

	/**
	 * 大于
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public static SimpleExpression gt(String fieldName, Object value) {
		if (value == null) {
			return null;
		}
		return new SimpleExpression(fieldName, value, Operator.GT);
	}

	/**
	 * 小于
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public static SimpleExpression lt(String fieldName, Object value) {
		if (value == null) {
			return null;
		}
		return new SimpleExpression(fieldName, value, Operator.LT);
	}

	/**
	 * 大等于
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public static SimpleExpression gte(String fieldName, Object value) {
		if (value == null) {
			return null;
		}
		return new SimpleExpression(fieldName, value, Operator.GTE);
	}

	/**
	 * 小等于
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public static SimpleExpression lte(String fieldName, Object value) {
		if (value == null) {
			return null;
		}
		return new SimpleExpression(fieldName, value, Operator.LTE);
	}

	/**
	 * 并且
	 * 
	 * @param criterions
	 * @return
	 */
	public static LogicalExpression and(Criterion... criterions) {
		return new LogicalExpression(criterions, Operator.AND);
	}

	/**
	 * 或者
	 * 
	 * @param criterions
	 * @return
	 */
	public static LogicalExpression or(Criterion... criterions) {
		return new LogicalExpression(criterions, Operator.OR);
	}
}
