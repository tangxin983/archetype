package com.github.tx.archetype.modules.core;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 基础增删改查服务
 * 
 * @author tangx
 * @since 2014年12月18日
 */

public abstract class BaseService<T, ID extends Serializable> {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private JpaRepository<T, ID> repo;

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
		for(ID id : ids){
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
