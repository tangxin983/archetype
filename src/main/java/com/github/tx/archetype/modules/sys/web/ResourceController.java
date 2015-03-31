package com.github.tx.archetype.modules.sys.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.tx.archetype.modules.core.BaseController;
import com.github.tx.archetype.modules.sys.entity.Resource;
import com.github.tx.archetype.modules.sys.service.ResourceService;

@Controller
@RequestMapping("/sys/resource")
public class ResourceController extends BaseController<Resource, Long> {

	@Autowired
	private ResourceService resourceService;

	/**
	 * 列表页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping
	public String view(Model model) {
		model.addAttribute("entitys", resourceService.findAllResourceByOrder());
		return "modules/sys/resourceList";
	}

	/**
	 * 新增页
	 * 
	 * @param parentId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(
			@RequestParam(value = "parentId", required = false) Long parentId,
			Model model) {
		parentId = parentId == null ? 1l : parentId;// 如果没有传入父菜单id，则默认父菜单是顶级菜单
		Resource parent = resourceService.findOne(parentId);
		Resource resource = new Resource();
		resource.setParent(parent);
		model.addAttribute("entity", resource);
		return "modules/sys/resourceForm";
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
		model.addAttribute("entity", resourceService.findOne(id));
		return "modules/sys/resourceForm";
	}

	/**
	 * 保存操作（id为空新增不为空更新）
	 * 
	 * @param entity
	 *            资源实体
	 * @param parentId
	 *            父资源ID
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute("entity") Resource entity,
			BindingResult result, @RequestParam("parentId") Long parentId,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {// @Valid进行javaBean校验如果失败则直接返回列表页并显示错误,注意BindingResult一定要放在entity参数后
			addMessage(redirectAttributes, getFieldErrorMessage(result));
			return "redirect:/sys/resource";
		}
		resourceService.saveResource(entity, parentId);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:/sys/resource";
	}

	/**
	 * 删除
	 */
	@RequestMapping("delete/{id}")
	public String delete(@PathVariable("id") Long id,
			RedirectAttributes redirectAttributes) {
		resourceService.delete(id);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:/sys/resource";
	}

	/**
	 * 加载资源树数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "treeData")
	@ResponseBody
	public List<Map<String, Object>> treeData() {
		List<Map<String, Object>> mapList = new ArrayList<>();
		List<Resource> resources = resourceService.findAllResourceByOrder();
		for (Resource resource : resources) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", resource.getId());
			if (resource.getParent() != null) {
				map.put("pId", resource.getParent().getId());
			} else {
				map.put("pId", 0);
			}
			map.put("name", resource.getResourceName());
			mapList.add(map);
		}
		return mapList;
	}

}
