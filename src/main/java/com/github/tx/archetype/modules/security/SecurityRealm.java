package com.github.tx.archetype.modules.security;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.tx.archetype.common.util.CollectionUtils;
import com.github.tx.archetype.modules.sys.entity.Resource;
import com.github.tx.archetype.modules.sys.entity.Role;
import com.github.tx.archetype.modules.sys.entity.User;
import com.github.tx.archetype.modules.sys.entity.UserInfo;
import com.github.tx.archetype.modules.sys.service.UserService;

/**
 * 身份认证、授权
 * 
 * @author tangx
 * @since 2015年1月27日
 */
public class SecurityRealm extends AuthorizingRealm {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserService userService;

	/**
	 * 身份认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authenticationToken)
			throws AuthenticationException {
		UsernamePasswordTokenExtend token = (UsernamePasswordTokenExtend) authenticationToken;
		Session session = SecurityUtils.getSubject().getSession();
		Integer number = (Integer) session
				.getAttribute(CaptchaAuthenticationFilter.DEFAULT_LOGIN_NUM_PARAM);
		// 超出登录失败次数阈值后需校验验证码
		if (number > CaptchaAuthenticationFilter.DEFAULT_ALLOW_LOGIN_NUM) {
			// 获取系统验证码
			String sysCaptcha = (String) session
					.getAttribute(CaptchaAuthenticationFilter.DEFAULT_CAPTCHA_PARAM);
			// 获取用户输入的验证码
			String userCaptcha = token.getCaptcha();
			// 如果验证码不匹配，登录失败
			if (StringUtils.isEmpty(userCaptcha)
					|| !StringUtils.equalsIgnoreCase(sysCaptcha, userCaptcha)) {
				throw new CaptchaException();
			}
		}
		// 根据登录名获取用户
		User user = userService.findByLoginName(token.getUsername());
		// 判读用户是否存在
		if (user == null) {
			throw new UnknownAccountException();
		}
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				user, // 用户实体
				user.getPassword(), // 密码
				ByteSource.Util.bytes(user.getCredentialsSalt()),// salt=loginName+salt
				getName());
		return authenticationInfo;
	}

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		User principal = (User) principals.getPrimaryPrincipal();
		User user = userService.findByLoginName(principal.getLoginName());
		Set<Role> roles = user.getRoles();// 用户角色集合
		Set<Resource> resources = new HashSet<>();// 用户资源集合
		for (Role role : roles) {
			resources.addAll(role.getResources());
		}
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		// 添加用户角色到shiro
		Set<String> roleStrings = CollectionUtils.extractToSet(roles, "roleName", true);
		authorizationInfo.addRoles(roleStrings);
		// 添加用户资源权限到shiro
		Set<String> permStrings = getStringPermissions(resources);
		authorizationInfo.addStringPermissions(permStrings);
		logger.debug("加载{}的权限：", user.getLoginName());
		logger.debug("角色：{}", roleStrings.toString());
		logger.debug("资源：{}", permStrings.toString());
		// 将用户相关信息放入session
		Session session = SecurityUtils.getSubject().getSession();
		UserInfo userInfo = new UserInfo();
		userInfo.setUser(user);
		userInfo.setRoles(roles);
		userInfo.setResources(resources);
		userInfo.setMenus(getMenus(resources));
		session.setAttribute("userInfo", userInfo);
		return authorizationInfo;
	}

	/**
	 * 获取资源权限(即资源的ID集合)
	 * 
	 * @param resources
	 * @return
	 */
	private Set<String> getStringPermissions(Set<Resource> resources) {
		Set<Long> idSet = CollectionUtils.extractToSet(resources, "id", true);
		Set<String> permSet = new LinkedHashSet<>();
		for (Long perm : idSet) {
			permSet.add(String.valueOf(perm));
		}
		return permSet;
	}

	/**
	 * 获取用户菜单
	 * 
	 * @param resources
	 * @return
	 */
	private Set<Resource> getMenus(Set<Resource> resources) {
		Set<Long> resourceIds = CollectionUtils.extractToSet(resources, "id", true);//用户资源权限ID
		Set<Resource> menus = new LinkedHashSet<>();
		for (Resource menu : resources) {
			if (menu.getParent() != null && menu.getParent().getId() == 1 && menu.getType() == 1) {//选取一级菜单
				if(menu.getChildren() != null && menu.getChildren().size() > 0){
					// 会关联出一级菜单的所有子菜单，需在此处删掉不在用户权限ID范围内的子菜单
					Iterator<Resource> children = menu.getChildren().iterator();
					while(children.hasNext()){
						Resource child = (Resource) children.next();
						if(!resourceIds.contains(child.getId())){
							children.remove();
						}
					}
				}
				menus.add(menu);
			}
		}
		return menus;
	}

}
