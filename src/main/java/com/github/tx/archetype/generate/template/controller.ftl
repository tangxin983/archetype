package ${packageName}.${moduleName}.web${subModuleName};

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.PageRequest;
import com.github.tx.archetype.modules.core.BaseController;
import ${packageName}.${moduleName}.entity${subModuleName}.${ClassName};
import ${packageName}.${moduleName}.service${subModuleName}.${ClassName}Service;

/**
 * ${functionName}Controller
 * @author ${classAuthor}
 * @since ${classVersion}
 */
@Controller
@RequestMapping(value = "${urlPrefix}")
public class ${ClassName}Controller extends BaseController {

	@Autowired
	private ${ClassName}Service ${className}Service;
	 
	<#if isPagination>
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
		model.addAttribute("page", ${className}Service.dynamicQuery(request, new PageRequest(page - 1, size)));
		model.addAttribute("searchParams", this.getQueryString(request));
		return "modules/${urlPrefix}List";
	}	
	<#else>
	/**
	 * 列表页
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping
	public String list(Model model, HttpServletRequest request) {
		model.addAttribute("entitys", ${className}Service.dynamicQuery(request));
		return "modules/${urlPrefix}List";
	}
	</#if> 
	
	/**
	 * 新建页
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		return "modules/${urlPrefix}Form";
	}
 
	/**
	 * 更新页
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("entity", ${className}Service.findOne(id));
		return "modules/${urlPrefix}Form";
	}
	
	/**
	 * 保存操作（id为空新增不为空更新）
	 * @param entity
	 * @param result
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(
			@Valid @ModelAttribute("entity") ${ClassName} entity, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			addMessage(redirectAttributes, getFieldErrorMessage(result));
			return "redirect:/${urlPrefix}";
		}
		${className}Service.save(entity);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:/${urlPrefix}";
	}
	
	/**
	 * 删除
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		${className}Service.delete(id);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:/${urlPrefix}";
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("delete")
	public String multiDel(@RequestParam("ids")List<Long> ids, RedirectAttributes redirectAttributes) {
		${className}Service.delete(ids);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:/${urlPrefix}";
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
			model.addAttribute("entity", ${className}Service.findOne(id));
		}
		model.addAttribute("module", getControllerContext());// 项目上下文
		model.addAttribute("ctxModule", servletContext.getContextPath() + "/" + getControllerContext());// 模块上下文
	}

}
