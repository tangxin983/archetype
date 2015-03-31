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
public class ${ClassName}Controller extends BaseController<${ClassName}, Long> {

	@Autowired
	private ${ClassName}Service ${className}Service;
	 
	/**
	 * 列表页
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping
	public String list(Model model, HttpServletRequest request) {
		model.addAttribute("entitys", ${className}Service.findAll());
		return "modules/${urlPrefix}List";
	}
	
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

}
