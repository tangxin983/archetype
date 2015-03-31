package com.github.tx.archetype.modules.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

/**
 * 验证码表单登录拦截器
 * 
 * @author tangx
 *
 */
public class CaptchaAuthenticationFilter extends FormAuthenticationFilter {

	// 验证码参数名称
	public static final String DEFAULT_CAPTCHA_PARAM = "captcha";
	// 登录次数超出allowLoginNum时，是否展示验证码的参数名称
	public static final String DEFAULT_SHOW_CAPTCHA_PARAM = "showCaptcha";
	// 登录次数参数名称
	public static final String DEFAULT_LOGIN_NUM_PARAM = "loginNum";
	// 允许登录次数，当登录次数大于该数值时，会在页面中显示验证码
	public static final Integer DEFAULT_ALLOW_LOGIN_NUM = 2;

	/**
	 * 登录时先将计数+1
	 */
	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
		Session session = getSubject(request, response).getSession();
		// 获取登录次数
		Integer number = (Integer) session.getAttribute(DEFAULT_LOGIN_NUM_PARAM);
		// 首次登录，将该数量记录在session中，否则+1
		if (number == null) {
			number = new Integer(1);
			session.setAttribute(DEFAULT_LOGIN_NUM_PARAM, number);
		} else {
			number++;
			session.setAttribute(DEFAULT_LOGIN_NUM_PARAM, number);
		}
		return super.executeLogin(request, response);
	}

	/**
	 * 登录失败次数大等于ALLOW_LOGIN_NUM时，将显示验证码
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token,
			AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		Session session = getSubject(request, response).getSession();
		// 获取登录失败次数
		Integer number = (Integer) session.getAttribute(DEFAULT_LOGIN_NUM_PARAM);
		// 大等于阈值显示验证码
		if (number >= DEFAULT_ALLOW_LOGIN_NUM) {
			session.setAttribute(DEFAULT_SHOW_CAPTCHA_PARAM, true);
		}
		return super.onLoginFailure(token, e, request, response);
	}

	/**
	 * 登录成功则重置计数
	 */
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token,
			Subject subject, ServletRequest request, ServletResponse response)
			throws Exception {
		Session session = getSubject(request, response).getSession();
		session.removeAttribute(DEFAULT_LOGIN_NUM_PARAM);
		session.removeAttribute(DEFAULT_SHOW_CAPTCHA_PARAM);
		return super.onLoginSuccess(token, subject, request, response);
	}

	/**
	 * 创建自定义的含验证码的令牌类{@link UsernamePasswordTokenExtend}
	 */
	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		String host = getHost(request);
		boolean rememberMe = isRememberMe(request);
		// 获取用户输入的验证码
		String captcha = WebUtils.getCleanParam(request, DEFAULT_CAPTCHA_PARAM);
		return new UsernamePasswordTokenExtend(username, password, rememberMe, host, captcha);
	}

	 
}