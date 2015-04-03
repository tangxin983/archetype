<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>${functionName}管理</title>
	<!-- 这里引入额外的css和js 
	<link rel="stylesheet" type="text/css" href="" />
	<script type="text/javascript" src=""></script>
	-->
	<script type="text/javascript">
	function del(){
		$("[name='ids']").each(function(){
			if($(this).is(":checked")){
				return confirmx_func('确定要删除选中的记录吗?', function(){$("#viewForm").submit();})
			}
		});
	}
	</script>
</head>
<body>
	<tags:message content="${r"${message}"}" />

	<!-- search form -->
	<nav class="navbar navbar-default">
		<form class="navbar-form navbar-left" valid="false">
			<#list entityFields as field>
			<div class="form-group">
				<input name="lk_${field.name}" value="${r"${param.lk_"}${field.name}}" class="form-control" placeholder="${field.colRemark}">
			</div>
			</#list>
			<button type="submit" class="btn btn-primary">
				<span class="glyphicon glyphicon-search"></span> 查询
			</button>
			<a href="${r"${ctxModule}"}/create" class="btn btn-primary"> 
				<span class="glyphicon glyphicon-plus"></span> 添加
			</a>
			<a onclick="del()" class="btn btn-danger">
				<span class="glyphicon glyphicon-remove"></span> 删除
			</a>
		</form>
	</nav>

	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="text-muted bootstrap-admin-box-title">${functionName}列表</div>
		</div>
		<div class="panel-body">
			<form id="viewForm" action="${r"${ctxModule}"}/delete" valid="false" method="post">
				<table class="table table-striped table-hover">
					<thead>
						<tr>
							<th><input type="checkbox" id="selectAll"></th>
							<#list entityFields as field>
							<th>${field.colRemark}</th>
							</#list>
						</tr>
					</thead>
					<tbody>
						<#if isPagination>
						<c:forEach items="${r"${page.content}"}" var="entity">
						<#else>
						<c:forEach items="${r"${entitys}"}" var="entity">
						</#if>
							<tr>
								<td><input type="checkbox" name="ids" value="${r"${entity.id}"}"></td>
								<#list entityFields as field>
								<#if field_index == 0>
		    					<td>
		    						<a href="${r"${ctxModule}"}/update/${r"${entity.id}"}" title="修改">
										${r"${entity."}${field.name}}
									</a>
		    					</td>
		    					<#else>
			    				<#if field.type == 'Date'>
								<td><fmt:formatDate value="${r"${entity."}${field.name}}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<#else>
								<td>${r"${entity."}${field.name}}</td>
								</#if>
								</#if>
								</#list>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</form>	 
			<#if isPagination>
			<tags:pagination page="${r"${page}"}" />
			</#if>
		</div>
	</div>
	
</body>
</html>
