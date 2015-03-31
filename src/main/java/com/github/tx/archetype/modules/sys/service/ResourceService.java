package com.github.tx.archetype.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.tx.archetype.modules.core.BaseService;
import com.github.tx.archetype.modules.sys.dao.ResourceRepository;
import com.github.tx.archetype.modules.sys.entity.Resource;

/**
 * 角色服务
 * 
 * @author tangx
 * @since 2014年12月23日
 */
@Service
@Transactional
public class ResourceService extends BaseService<Resource, Long> {

	@Autowired
	private ResourceRepository resourceRepository;

	/**
	 * 按顺序获取所有资源
	 * 
	 * @return
	 */
	public List<Resource> findAllResourceByOrder() {
		return resourceRepository.findByOrderBySortAsc();
	}

	/**
	 * 按顺序获取需要控制的资源(href不为空)
	 * 
	 * @return
	 */
	public List<Resource> findAccessControlResourceByOrder() {
		return resourceRepository.findByHrefNotNullOrderBySortAsc();
	}

	/**
	 * 保存资源
	 * 
	 * @param entity
	 *            资源实体
	 * @param parentId
	 *            上级资源ID
	 * @return 保存后的资源实体
	 */
	public Resource saveResource(Resource entity, Long parentId) {
		Resource parent = new Resource();
		parent.setId(parentId);
		entity.setParent(parent);
		return resourceRepository.save(entity);
	}

}
