package com.github.tx.archetype.modules.sys.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.tx.archetype.modules.core.BaseController;
import com.github.tx.archetype.modules.sys.entity.User;
import com.github.tx.archetype.modules.sys.service.RoleService;
import com.github.tx.archetype.modules.sys.service.UserService;

@Controller
@RequestMapping("/sys/user")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	/**
	 * 列表页
	 * @param page
	 * @param size
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping
	public String list(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = DEFAULT_PAGE_SIZE) int size,
			Model model, HttpServletRequest request) {
		model.addAttribute("page", userService.dynamicQuery(request, new PageRequest(page - 1, size)));
		model.addAttribute("searchParams", this.getQueryString(request));
		return "modules/sys/userList";
	}

	/**
	 * 新建页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("roles", roleService.findAll());// 显示角色列表
		return "modules/sys/userForm";
	}

	/**
	 * 更新页
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("roles", roleService.findAll());// 显示角色列表
		model.addAttribute("entity", userService.findOne(id));
		return "modules/sys/userForm";
	}

	/**
	 * 保存操作（id为空新增不为空更新）
	 * 
	 * @param entity
	 * @param roleIds
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(
			@Valid @ModelAttribute("entity") User entity,
			BindingResult result,
			@RequestParam(value = "roleIds", required = false) List<Long> roleIds,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			addMessage(redirectAttributes, getFieldErrorMessage(result));
			return "redirect:/sys/user";
		}
		userService.saveUser(entity, roleIds);// 注意需添加role集合，否则sys_user_role记录不会被自动维护
		addMessage(redirectAttributes, "保存成功");
		return "redirect:/sys/user";
	}

	/**
	 * 批量删除
	 */
	@RequestMapping("delete")
	public String multiDel(@RequestParam("ids") List<Long> ids,
			RedirectAttributes redirectAttributes) {
		userService.delete(ids);// 这里没有设置role集合，所以在删除用户后会自动删除此用户对应的sys_user_role记录
		addMessage(redirectAttributes, "删除成功");
		return "redirect:/sys/user";
	}
	
	/**
	 * 在每个controller方法开始前执行，作用如下：
	 * <p>
	 * 1、设置上下文参数供前端页面使用
	 * <p>
	 * 2、如果表单id参数不为空，说明是更新操作。此时先根据id从数据库查出对象,再把表单提交的内容绑定到该对象上，
	 * 避免表单字段不完整更新为null的情况
	 */
	@ModelAttribute
	public void populateModel(
			@RequestParam(value = "id", required = false) Long id, Model model) {
		if (id != null) {
			model.addAttribute("entity", userService.findOne(id));
		}
		model.addAttribute("module", getControllerContext());// 项目上下文
		model.addAttribute("ctxModule", servletContext.getContextPath() + "/" + getControllerContext());// 模块上下文
	}
}
