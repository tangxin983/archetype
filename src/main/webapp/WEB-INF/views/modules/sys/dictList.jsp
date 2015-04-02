<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>字典管理</title>
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
	<tags:message content="${message}" />

	<!-- search form -->
	<nav class="navbar navbar-default">
		<form class="navbar-form navbar-left" valid="false">
			<div class="form-group">
				<input name="s_label" value="${param.s_label}" class="form-control" placeholder="字典名称">
			</div>
			<div class="form-group">
				<input name="s_type" value="${param.s_type}" class="form-control" placeholder="字典类型">
			</div>
			<a href="${ctxModule}/create" class="btn btn-primary"> 
				<span class="glyphicon glyphicon-plus"></span> 添加
			</a>
			<a onclick="del()" class="btn btn-danger">
				<span class="glyphicon glyphicon-remove"></span> 删除
			</a>
		</form>
	</nav>

	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="text-muted bootstrap-admin-box-title">字典列表</div>
		</div>
		<div class="panel-body">
			<form id="viewForm" action="${ctxModule}/delete" valid="false" method="post">
				<table class="table table-striped table-hover">
					<thead>
						<tr>
							<th><input type="checkbox" id="selectAll"></th>
							<th>字典名称</th>
							<th>字典值</th>
							<th>字典类型</th>
							<th>描述</th>
							<th>创建者</th>
							<th>创建时间</th>
							<th>上次更新者</th>
							<th>上次更新时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${entitys}" var="entity">
							<tr>
								<td><input type="checkbox" name="ids" value="${entity.id}"></td>
		    					<td>
		    						<a href="${ctxModule}/update/${entity.id}" title="修改">
										${entity.label}
									</a>
		    					</td>
								<td>${entity.value}</td>
								<td>${entity.type}</td>
								<td>${entity.description}</td>
								<td>${entity.createBy}</td>
								<td><fmt:formatDate value="${entity.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td>${entity.updateBy}</td>
								<td><fmt:formatDate value="${entity.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</form>	 
		</div>
	</div>
	
</body>
</html>
