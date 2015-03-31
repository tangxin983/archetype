package com.github.tx.archetype.modules.sys.web;

import java.io.ByteArrayOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.tx.archetype.common.util.CaptchaUtils;
import com.github.tx.archetype.modules.security.CaptchaAuthenticationFilter;
import com.github.tx.archetype.modules.security.CaptchaException;

/**
 * 登录控制器
 * 
 */
@Controller
public class LoginController {

	/**
	 * 登录页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm() {
		Subject subject = SecurityUtils.getSubject();
		// 如果是已登录或记住状态跳转首页
		if (subject.isAuthenticated() || subject.isRemembered()) {
			return "redirect:/index";
		}
		return "modules/sys/login";
	}

	/**
	 * 登录失败
	 * 
	 * @param userName
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginFail(@RequestParam("username") String userName,
			HttpServletRequest req) {
		Subject subject = SecurityUtils.getSubject();
		// 如果是已登录或记住状态跳转首页
		if (subject.isAuthenticated() || subject.isRemembered()) {
			return "redirect:/index";
		}
		String exceptionClassName = (String) req.getAttribute("shiroLoginFailure");
		String error = null;
		if (UnknownAccountException.class.getName().equals(exceptionClassName)
				|| IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
			error = "用户名或密码错误";
		} else if (CaptchaException.class.getName().equals(exceptionClassName)) {
			error = "验证码错误";
		} else {
			error = "服务异常请联系管理员";
		}
		req.setAttribute("username", userName);// 表单填充登录失败时的用户名，避免再填
		req.setAttribute("loginError", error);
		return "modules/sys/login";
	}

	/**
	 * 生成验证码并放入session
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getCaptcha", method = RequestMethod.GET, produces = MediaType.IMAGE_GIF_VALUE)
	@ResponseBody
	public byte[] getCaptcha() {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String captcha = CaptchaUtils.getGifCaptcha(80, 32, 4, outputStream, 1000).toLowerCase();
		SecurityUtils.getSubject().getSession().setAttribute(CaptchaAuthenticationFilter.DEFAULT_CAPTCHA_PARAM,captcha);
		return outputStream.toByteArray();
	}
	
	/**
	 * 首页视图
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "modules/sys/index";
	}
	
	/**
	 * 没有授权错误页面
	 * @return
	 */
	@RequestMapping("/unauthorized")
	public String unauthorized() {
		return "modules/sys/unauthorized";
	}

}
