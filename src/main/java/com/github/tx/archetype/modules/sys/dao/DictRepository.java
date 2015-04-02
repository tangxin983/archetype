package com.github.tx.archetype.modules.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.github.tx.archetype.modules.sys.entity.Dict;

/**
 * 字典Dao
 * @author tangx
 * @since 2015-04-02
 */
public interface DictRepository extends JpaRepository<Dict, Long> {
	
}
