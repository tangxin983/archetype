package com.github.tx.archetype.modules.core;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.github.tx.archetype.common.util.Servlets;
import com.github.tx.archetype.modules.core.jpa.criteria.Criteria;
import com.github.tx.archetype.modules.core.jpa.criteria.Restrictions;

/**
 * 基础增删改查服务
 * 
 * @author tangx
 * @since 2014年12月18日
 */

public abstract class BaseService<T, ID extends Serializable> {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BaseRepository<T, ID> repo;

	/**
	 * 新增或更新记录(主键为null则新增否则更新)
	 * 
	 * @param entity
	 *            实体
	 * @return 保存后的实体
	 */
	@Transactional
	public T save(T entity) {
		return repo.save(entity);
	}

	/**
	 * 批量新增或更新记录(主键为null则新增否则更新)
	 * 
	 * @param entities
	 *            实体集合
	 * @return 保存后的实体集合
	 */
	@Transactional
	public List<T> save(Iterable<T> entities) {
		return repo.save(entities);
	}

	/**
	 * 根据主键获取记录
	 * 
	 * @param id
	 *            主键
	 * @return 主键对应的实体，如果找不到返回null
	 */
	public T findOne(ID id) {
		return repo.findOne(id);
	}

	/**
	 * 获取所有记录
	 * 
	 * @return 实体集合
	 */
	public List<T> findAll() {
		return repo.findAll();
	}

	/**
	 * 分页获取记录
	 * 
	 * @param pageable
	 *            分页参数
	 * @return 分页对象
	 */
	public Page<T> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}

	/**
	 * 动态查询
	 * 
	 * @param request
	 * @return 实体集合
	 */
	public List<T> dynamicQuery(HttpServletRequest request) {
		return repo.findAll(buildCriteria(request));
	}
	
	/**
	 * 动态查询（分页）
	 * @param request
	 * @param pageable
	 * @return
	 */
	public Page<T> dynamicQuery(HttpServletRequest request, Pageable pageable) {
		return repo.findAll(buildCriteria(request), pageable);
	}
	
	/**
	 * 构建规范查询
	 * @param request
	 * @return 规范查询对象
	 */
	private Criteria<T> buildCriteria(HttpServletRequest request){
		Criteria<T> c = new Criteria<T>();
		// 等于
		Map<String, Object> eqParams = Servlets.getParametersStartingWith(request, "eq_", true);
		for (String key : eqParams.keySet()) {
			c.add(Restrictions.eq(key, eqParams.get(key)));
		}
		// 不等于
		Map<String, Object> neParams = Servlets.getParametersStartingWith(request, "ne_", true);
		for (String key : neParams.keySet()) {
			c.add(Restrictions.ne(key, neParams.get(key)));
		}
		// 模糊匹配
		Map<String, Object> lkParams = Servlets.getParametersStartingWith(request, "lk_", true);
		for (String key : lkParams.keySet()) {
			c.add(Restrictions.like(key, lkParams.get(key)));
		}
		// 大于
		Map<String, Object> gtParams = Servlets.getParametersStartingWith(request, "gt_", true);
		for (String key : gtParams.keySet()) {
			c.add(Restrictions.gt(key, gtParams.get(key)));
		}
		// 小于
		Map<String, Object> ltParams = Servlets.getParametersStartingWith(request, "lt_", true);
		for (String key : ltParams.keySet()) {
			c.add(Restrictions.lt(key, ltParams.get(key)));
		}
		// 大等于
		Map<String, Object> gteParams = Servlets.getParametersStartingWith(request, "gte_", true);
		for (String key : gteParams.keySet()) {
			c.add(Restrictions.gte(key, gteParams.get(key)));
		}
		// 小等于
		Map<String, Object> lteParams = Servlets.getParametersStartingWith(request, "lte_", true);
		for (String key : lteParams.keySet()) {
			c.add(Restrictions.lte(key, lteParams.get(key)));
		}
		return c;
	}

	/**
	 * 获取记录数
	 * 
	 * @return 记录数
	 */
	public long count() {
		return repo.count();
	}

	/**
	 * 删除主键对应的记录
	 * 
	 * @param id
	 *            主键
	 */
	@Transactional
	public void delete(ID id) {
		repo.delete(id);
	}

	/**
	 * 批量删除主键对应的记录
	 * 
	 * @param ids
	 *            主键列表
	 */
	@Transactional
	public void delete(Iterable<ID> ids) {
		for (ID id : ids) {
			repo.delete(id);
		}
	}

	/**
	 * 删除指定的实体
	 * 
	 * @param 实体
	 */
	@Transactional
	public void delete(T entity) {
		repo.delete(entity);
	}

	/**
	 * 删除所有记录
	 */
	@Transactional
	public void deleteAll() {
		repo.deleteAll();
	}
}
