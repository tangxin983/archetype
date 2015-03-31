package com.github.tx.archetype.modules.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.github.tx.archetype.modules.sys.entity.Role;

/**
 * Role DAO
 * 
 * @author tangx
 * @since 2015年1月27日
 */

public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
	 
}
