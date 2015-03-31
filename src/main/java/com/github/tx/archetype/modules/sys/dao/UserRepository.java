package com.github.tx.archetype.modules.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.github.tx.archetype.modules.sys.entity.User;

/**
 * User DAO
 * 
 * @author tangx
 * @since 2015年1月27日
 */

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

	User findByLoginName(String loginName);
}
