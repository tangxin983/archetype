package ${packageName}.${moduleName}.dao${subModuleName};

import org.springframework.data.jpa.repository.JpaRepository;
import ${packageName}.${moduleName}.entity${subModuleName}.${ClassName};

/**
 * ${functionName}Dao
 * @author ${classAuthor}
 * @since ${classVersion}
 */
public interface ${ClassName}Repository extends JpaRepository<${ClassName}, Long> {
	
}
