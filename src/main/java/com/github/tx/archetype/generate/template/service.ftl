package ${packageName}.${moduleName}.service${subModuleName};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.tx.archetype.modules.core.BaseService;
import ${packageName}.${moduleName}.entity${subModuleName}.${ClassName};
import ${packageName}.${moduleName}.dao${subModuleName}.${ClassName}Repository;

/**
 * ${functionName}Service
 * @author ${classAuthor}
 * @since ${classVersion}
 */
@Service
@Transactional
public class ${ClassName}Service extends BaseService<${ClassName}, Long> {

	@Autowired
	private ${ClassName}Repository ${className}Repository;
	
}
