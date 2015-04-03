package com.github.tx.archetype.modules.core;

import java.io.Serializable;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;

/**
 * 控制器基类
 * 
 * @author tangx
 * @since 2014年12月18日
 */

public abstract class BaseController<T, ID extends Serializable> implements ServletContextAware {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected static final String DEFAULT_PAGE_SIZE = "3"; 

	@Autowired
	private BaseService<T, ID> service;

	protected ServletContext servletContext;

	@Override
	public void setServletContext(ServletContext context) {
		this.servletContext = context;
	}

	/**
	 * 在每个controller方法开始前执行，作用如下：<p>
	 * 1、设置上下文参数供前端页面使用<p>
	 * 2、如果表单id参数不为空，说明是更新操作。此时先根据id从数据库查出对象,再把表单提交的内容绑定到该对象上，避免表单字段不完整更新为null的情况
	 */
	@ModelAttribute
	protected void populateModel(
			@RequestParam(value = "id", required = false) ID id, Model model) {
		if (id != null) {
			model.addAttribute("entity", service.findOne(id));
		}
		model.addAttribute("module", getControllerContext());// 项目上下文
		model.addAttribute("ctxModule", servletContext.getContextPath() + "/" + getControllerContext());// 模块上下文
	}

	/**
	 * 添加flash消息
	 * 
	 * @param redirectAttributes
	 * @param messages
	 */
	protected void addMessage(RedirectAttributes redirectAttributes,
			String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages) {
			sb.append(message).append(messages.length > 1 ? "<br/>" : "");
		}
		redirectAttributes.addFlashAttribute("message", sb.toString());
	}
	
	/**
	 * 获取服务端参数验证错误信息
	 * 
	 * @param result
	 */
	protected String[] getFieldErrorMessage(BindingResult result) {
		List<String> message = Lists.newArrayList();
		for (FieldError error : result.getFieldErrors()) {
			message.add(error.getDefaultMessage());
		}
		message.add(0, "数据校验失败：");
		return message.toArray(new String[] {});
	}
	
	/**
	 * 获取查询字符串（去掉page参数）
	 * @param request
	 * @return
	 */
	protected String getQueryString(HttpServletRequest request) {
		String queryStr = request.getQueryString();
		if(StringUtils.isNotBlank(queryStr)){
			if(queryStr.indexOf("?page=") != -1){
				queryStr = queryStr.substring(queryStr.indexOf("&") + 1);
			}
		}
		return queryStr;
	}

	/**
	 * 通过反射取得controller的上下文（头尾的'/'会被去掉）
	 * 
	 * @return 上下文字符串
	 */
	private String getControllerContext() {
		String context = "";
		Class<?> c = this.getClass();
		if (c != null) {
			RequestMapping mapping = c.getAnnotation(RequestMapping.class);
			String[] mappingValues = mapping.value();
			context = mappingValues[0];
			if (context.startsWith("/")) {
				context = context.substring(1);
			}
			if (context.endsWith("/")) {
				context = context.substring(0, context.length() - 1);
			}
		}
		return context;
	}
}
