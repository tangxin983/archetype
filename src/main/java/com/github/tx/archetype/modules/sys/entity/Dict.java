package com.github.tx.archetype.modules.sys.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * 字典实体
 * @author tangx
 * @since 2015-04-02
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "sys_dict")
public class Dict extends IdEntity {

	@Column(name = "label")
	@NotNull(message = "字典名称不能为空")
	private String label; //字典名称
	
	@Column(name = "value")
	@NotNull(message = "字典值不能为空")
	private String value; //字典值
	
	@Column(name = "type")
	@NotNull(message = "字典类型不能为空")
	private String type; //字典类型
	
	@Column(name = "description")
	@NotNull(message = "描述不能为空")
	private String description; //描述
	
	@CreatedBy
	@Column(name = "create_by")
	private String createBy; //创建者
	
	@CreatedDate
	@Column(name = "create_time")
	private Date createTime; //创建时间
	
	@LastModifiedBy
	@Column(name = "update_by")
	private String updateBy; //更新者
	
	@LastModifiedDate
	@Column(name = "update_time")
	private Date updateTime; //更新时间
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}


