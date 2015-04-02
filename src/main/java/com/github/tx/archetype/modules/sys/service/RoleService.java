package com.github.tx.archetype.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.tx.archetype.modules.core.BaseService;
import com.github.tx.archetype.modules.sys.dao.RoleRepository;
import com.github.tx.archetype.modules.sys.entity.Resource;
import com.github.tx.archetype.modules.sys.entity.Role;

/**
 * 角色服务
 * 
 * @author tangx
 * @since 2014年12月23日
 */
@Service
@Transactional
public class RoleService extends BaseService<Role, Long> {

	@Autowired
	private RoleRepository roleRepository;

	/**
	 * 
	 * 保存角色
	 * 
	 * @param entity
	 *            角色实体
	 * @param resourceIds
	 *            此角色实体对应的资源ID列表
	 * @return 保存后的角色实体
	 */
	public Role saveRole(Role entity, List<Long> resourceIds) {
		if (resourceIds != null && resourceIds.size() > 0) {
			for (Long resourceId : resourceIds) {
				Resource resource = new Resource();
				resource.setId(resourceId);
				entity.getResources().add(resource);
			}
		}
		return roleRepository.save(entity);
	}
}
