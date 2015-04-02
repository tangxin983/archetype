package com.github.tx.archetype.modules.core;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.data.domain.AuditorAware;

import com.github.tx.archetype.modules.sys.entity.User;

/**
 * 在创建更新时插入审计信息
 * 
 * @author tangx
 * @since 2015年4月2日
 */

public class SystemAuditorAware implements AuditorAware<String> {

	@Override
	public String getCurrentAuditor() {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		if (user != null && StringUtils.isNotBlank(user.getUserName())) {
			return user.getUserName();
		}
		return null;
	}

}
