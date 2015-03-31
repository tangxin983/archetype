package com.github.tx.archetype.modules.security;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.github.tx.archetype.modules.sys.entity.Resource;
import com.github.tx.archetype.modules.sys.service.ResourceService;

public class FilterChainFactoryBean implements FactoryBean<Map<String, String>> {
	
	Logger logger = LoggerFactory.getLogger(FilterChainFactoryBean.class);

	@Autowired
	private ResourceService resourceService;
	
	@Value("${shiro.default}")
	private String shiroDefault;// 配置文件中默认的权限控制

	/**
	 * 加载系统url权限控制
	 */
	@Override
	public Map<String, String> getObject() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		// 加载数据库权限控制
		List<Resource> resources = resourceService.findAccessControlResourceByOrder();
		for (Resource resource : resources) {
			String uri = resource.getHref().startsWith("/") ? resource.getHref() : "/" + resource.getHref();
			map.put(uri, "perms[" + resource.getId() + "]");
		}
		// 加载默认权限控制
		if(StringUtils.isNotBlank(shiroDefault)){
			for (String def : StringUtils.split(shiroDefault, ",")) {
				String[] kv = StringUtils.split(def, "=");
				if(kv != null){
					map.put(kv[0], kv[1]);
				}
			}
		}
		logger.debug("加载以下URL的权限控制:");
		for(String key : map.keySet()){
			logger.debug("url:{},权限:{}", key, map.get(key));
		}
		return map;
	}

	@Override
	public Class<?> getObjectType() {
		return Map.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
