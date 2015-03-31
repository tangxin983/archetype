package com.github.tx.archetype.modules.sys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.tx.archetype.modules.core.BaseService;
import com.github.tx.archetype.modules.security.PasswordHelper;
import com.github.tx.archetype.modules.sys.dao.UserRepository;
import com.github.tx.archetype.modules.sys.entity.Role;
import com.github.tx.archetype.modules.sys.entity.User;

/**
 * 
 * @author tangx
 * @since 2014年12月23日
 */
@Service
@Transactional
public class UserService extends BaseService<User, Long> {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordHelper helper;

	/**
	 * 根据登录名查找用户
	 * 
	 * @param loginName
	 * @return 用户实体
	 */
	public User findByLoginName(String loginName) {
		return userRepository.findByLoginName(loginName);
	}

	/**
	 * 保存用户
	 * 
	 * @param user
	 *            用户实体
	 * @param roleIds
	 *            用户对于的角色ID列表
	 * @return 保存后的用户实体
	 */
	public User saveUser(User user, List<Long> roleIds) {
		helper.encryptPassword(user);// 加密密码
		if (roleIds != null && roleIds.size() > 0) {
			for (Long roleId : roleIds) {
				Role role = new Role();
				role.setId(roleId);
				user.getRoles().add(role);
			}
		}
		return userRepository.save(user);
	}
	
	/**
	 * 根据前端页面条件做动态查询
	 * @param searchParams
	 * @return
	 */
	public List<User> dynamicQuery(final Map<String, Object> searchParams) {
		return userRepository.findAll(new Specification<User> () {
			@Override
			public Predicate toPredicate(Root<User> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if(searchParams.get("userName") != null){
					predicates.add(cb.like(root.get("userName").as(String.class), "%" + String.valueOf(searchParams.get("userName")) + "%"));
				}
				if(searchParams.get("loginName") != null){
					predicates.add(cb.like(root.get("loginName").as(String.class), "%" + String.valueOf(searchParams.get("loginName")) + "%"));
				}
				Predicate[] pre = new Predicate[predicates.size()];
				return query.where(predicates.toArray(pre)).getRestriction();
			} 
		});
	}
}
