package ${packageName}.${moduleName}.entity${subModuleName};

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * ${functionName}实体
 * @author ${classAuthor}
 * @since ${classVersion}
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "${tableName}")
public class ${ClassName} extends IdEntity {

	<#list entityFields as field>
	@Column(name = "${field.colName}")
	<#if field.notNull == '1'>
	@NotNull(message = "${field.colRemark}不能为空")
	</#if>
	<#if field.type == 'Date'>
	@Temporal(TemporalType.TIME)
	</#if>
	private ${field.type} ${field.name}; //${field.colRemark}
	
	</#list>
	
	<#list entityFields as field>
	public ${field.type} get${field.Name}() {
		return ${field.name};
	}

	public void set${field.Name}(${field.type} ${field.name}) {
		this.${field.name} = ${field.name};
	}
	
	</#list>
}


