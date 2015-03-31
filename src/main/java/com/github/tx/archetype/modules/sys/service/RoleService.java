package com.github.tx.archetype.modules.sys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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

	
	/**
	 * 根据前端页面条件做动态查询
	 * @param searchParams
	 * @return
	 */
	public List<Role> dynamicQuery(final Map<String, Object> searchParams) {
		return roleRepository.findAll(new Specification<Role> () {
			@Override
			public Predicate toPredicate(Root<Role> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if(searchParams.get("roleName") != null){
					predicates.add(cb.like(root.get("roleName").as(String.class), "%" + String.valueOf(searchParams.get("roleName")) + "%"));
				}
				Predicate[] pre = new Predicate[predicates.size()];
				return query.where(predicates.toArray(pre)).getRestriction();
			} 
		});
	}
}
