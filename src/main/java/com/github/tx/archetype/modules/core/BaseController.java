package com.github.tx.archetype.modules.core;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;

/**
 * 控制器基类
 * 
 * @author tangx
 * @since 2014年12月18日
 */

public abstract class BaseController implements ServletContextAware {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected static final String DEFAULT_PAGE_SIZE = "3";

	protected ServletContext servletContext;

	@Override
	public void setServletContext(ServletContext context) {
		this.servletContext = context;
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
	 * 
	 * @param request
	 * @return
	 */
	protected String getQueryString(HttpServletRequest request) {
		String queryStr = request.getQueryString();
		if (StringUtils.isNotBlank(queryStr)) {
			if (queryStr.indexOf("?page=") != -1) {
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
	protected String getControllerContext() {
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

	/**
	 * 业务异常处理
	 * 
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(ServiceException.class)
	protected String serviceExceptionHandler(HttpServletRequest request, ServiceException ex) {
		// TODO 数据库记录业务异常
		request.setAttribute("message", ex.getMessage());
		return "error/500";
	}

	/**
	 * rest异常处理
	 * 
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(RestException.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	protected String restExceptionHandler(HttpServletRequest request, RestException ex) {
		// TODO 数据库记录业务异常
		return ex.getMessage();
	}
}
