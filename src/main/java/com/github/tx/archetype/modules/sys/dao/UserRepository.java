package com.github.tx.archetype.modules.sys.dao;

import com.github.tx.archetype.modules.core.BaseRepository;
import com.github.tx.archetype.modules.sys.entity.User;

/**
 * User DAO
 * 
 * @author tangx
 * @since 2015年1月27日
 */

public interface UserRepository extends BaseRepository<User, Long> {

	User findByLoginName(String loginName);
}
