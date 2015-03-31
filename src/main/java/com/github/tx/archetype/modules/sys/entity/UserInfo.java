package com.github.tx.archetype.modules.sys.entity;

import java.io.Serializable;
import java.util.Set;

/**
 * 整合用户信息、角色信息、权限信息的实体
 * 
 * @author tangx
 *
 */
@SuppressWarnings("serial")
public class UserInfo implements Serializable {

	// 用户
	private User user;

	// 用户角色
	private Set<Role> roles;

	// 用户资源
	private Set<Resource> resources;

	// 用户菜单
	private Set<Resource> menus;

	public UserInfo() {
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Resource> getResources() {
		return resources;
	}

	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}

	public Set<Resource> getMenus() {
		return menus;
	}

	public void setMenus(Set<Resource> menus) {
		this.menus = menus;
	}
}
