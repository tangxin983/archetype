package com.github.tx.archetype.modules.sys.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.tx.archetype.modules.core.BaseController;
import com.github.tx.archetype.modules.core.RestException;
import com.github.tx.archetype.modules.core.ServiceException;

/** 
 * 业务异常和rest异常示例
 * @author tangx
 * @since 2015年4月3日
 */
@Controller
public class ExpExampleController extends BaseController {
	
	/**
	 * 被业务异常处理器捕获
	 * @return
	 */
	@RequestMapping("serviceExp")
	public String service() {
		throw new ServiceException("service fail");
	}
	
	/**
	 * 
	 * 被rest异常处理器捕获<p>
	 * 注意这里api/**不被装饰
	 * @return
	 */
	@RequestMapping("api/rest")
	@ResponseBody
	public String rest() {
		throw new RestException("rest fail");
	}
	
	/**
	 * 被容器捕获（web.xml）
	 * @return
	 */
	@RequestMapping("conExp")
	public String container() {
		throw new RuntimeException();
	}

}
