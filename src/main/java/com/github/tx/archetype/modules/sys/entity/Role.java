package com.github.tx.archetype.modules.sys.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.github.tx.archetype.common.util.CollectionUtils;

@SuppressWarnings("serial")
@Entity
@Table(name = "sys_role")
public class Role extends IdEntity {

	@Column(name = "role_name")
	private String roleName;

	private String description;

	@ManyToMany
	@JoinTable(name = "sys_role_resource", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = { @JoinColumn(name = "resource_id") })
	private Set<Resource> resources = new HashSet<>();

	@ManyToMany(mappedBy = "roles")
	private Set<User> users = new HashSet<>();

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Resource> getResources() {
		return resources;
	}

	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}

	@Transient
	public Set<Long> getResourceIds() {
		Set<Long> ids = CollectionUtils.extractToSet(this.resources, "id", true);
		return ids;
	}
}
