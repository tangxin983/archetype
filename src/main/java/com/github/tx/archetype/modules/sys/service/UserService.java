package com.github.tx.archetype.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
}
