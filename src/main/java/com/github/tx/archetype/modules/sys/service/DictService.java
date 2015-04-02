package com.github.tx.archetype.modules.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.tx.archetype.modules.core.BaseService;
import com.github.tx.archetype.modules.sys.entity.Dict;
import com.github.tx.archetype.modules.sys.dao.DictRepository;

/**
 * 字典Service
 * @author tangx
 * @since 2015-04-02
 */
@Service
@Transactional
public class DictService extends BaseService<Dict, Long> {

	@Autowired
	private DictRepository dictRepository;
	
}
