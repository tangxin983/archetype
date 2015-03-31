package com.github.tx.archetype.modules.sys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.tx.archetype.modules.sys.entity.Resource;

/**
 * Resource DAO
 * 
 * @author tangx
 * @since 2015年1月27日
 */

public interface ResourceRepository extends JpaRepository<Resource, Long> {

	// 相当于select * from sys_resource where href is not null order by sort asc
	/**
	 * 获取需要权限控制的资源(href不为空)
	 * @return
	 */
	List<Resource> findByHrefNotNullOrderBySortAsc();
	
	/**
	 * 获取所有资源
	 * @return
	 */
	List<Resource> findByOrderBySortAsc();
	
}
