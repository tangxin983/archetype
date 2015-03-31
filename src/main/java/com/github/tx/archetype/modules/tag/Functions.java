package com.github.tx.archetype.modules.tag;

import java.util.Collection;

/**
 * 自定义函数标签对应类
 * 
 * @author tangx
 * 
 */
public class Functions {

	public static <T> boolean contains(Collection<T> coll, Object o) {
		if (coll != null && !coll.isEmpty()) {
			return coll.contains(o);
		} else {
			return false;
		}
	}
	 
}
