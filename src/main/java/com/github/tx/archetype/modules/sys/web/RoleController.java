package com.github.tx.archetype.modules.sys.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.tx.archetype.common.util.Servlets;
import com.github.tx.archetype.modules.core.BaseController;
import com.github.tx.archetype.modules.sys.entity.Role;
import com.github.tx.archetype.modules.sys.service.RoleService;

/**
 * 角色管理控制器
 * 
 */
@Controller
@RequestMapping("/sys/role")
public class RoleController extends BaseController<Role, Long> {

	@Autowired
	private RoleService roleService;

	/**
	 * 列表页
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping
	public String list(Model model, HttpServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "s_");
		model.addAttribute("entitys", roleService.dynamicQuery(searchParams));
		// 将搜索条件编码成字符串，用于分页的URL
//		model.addAttribute("searchParams",
//				Servlets.encodeParameterStringWithPrefix(searchParams, "s_"));
		return "modules/sys/roleList";
	}

	/**
	 * 新建页
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		return "modules/sys/roleForm";
	}

	/**
	 * 更新页
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("entity", roleService.findOne(id));
		return "modules/sys/roleForm";
	}

	/**
	 * 保存操作（id为空新增不为空更新）
	 * @param entity
	 * @param resourceIds
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(
			@Valid @ModelAttribute("entity") Role entity, BindingResult result,
			@RequestParam(value = "resourceIds", required = false) List<Long> resourceIds,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			addMessage(redirectAttributes, getFieldErrorMessage(result));
			return "redirect:/sys/role";
		}
		roleService.saveRole(entity, resourceIds);//注意需添加resource集合，否则中间表记录不会被自动维护
		addMessage(redirectAttributes, "保存成功");
		return "redirect:/sys/role";
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("delete")
	public String multiDel(@RequestParam("ids")List<Long> ids,RedirectAttributes redirectAttributes) {
		roleService.delete(ids);//这里没有设置resource集合，所以在删除角色后会自动删除此角色对应的中间表记录
		addMessage(redirectAttributes, "删除成功");
		return "redirect:/sys/role";
	}

}
