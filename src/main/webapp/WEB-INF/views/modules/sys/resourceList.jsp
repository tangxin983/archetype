<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>菜单管理</title>
<%@include file="/WEB-INF/views/include/treetable.jsp"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#treeTable").treeTable({
			expandLevel : 3
		});
	});
</script>
</head>
<body>

	<tags:message content="${message}" />
	 
	<nav class="navbar navbar-default">
		<form class="navbar-form navbar-left" valid="false">
			<a href="${ctxModule}/create" class="btn btn-primary">
				<span class="glyphicon glyphicon-plus"></span> 添加一级菜单
			</a>
		</form>
	</nav>
	
	<!-- table -->
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="text-muted bootstrap-admin-box-title">资源列表</div>
		</div>
		<div class="panel-body">
			<table id="treeTable" class="table table-striped table-hover">
				<thead>
					<tr>
						<th>名称</th>
						<th>链接</th>
						<th>排序</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${entitys}" var="resource">
						<tr id="${resource.id}" pId="${resource.parent.id}">
							<td>
		    					<a href="${ctxModule}/update/${resource.id}" title="修改">
									${resource.resourceName}
								</a>
							</td>
							<td>${resource.href}</td>
							<td>${resource.sort}</td>
							<td>
							    <a href="${ctxModule}/delete/${resource.id}" class="btn btn-danger" title="删除"
							    	onclick="return confirmx('要删除该菜单及所有子菜单项吗?', this.href)">
							    	<span class="glyphicon glyphicon-remove"></span>
							    </a> 
							    <a href="${ctxModule}/create?parentId=${resource.id}" class="btn btn-primary" title="添加子菜单"> 
							    	<span class="glyphicon glyphicon-plus"></span>
								</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

</body>
</html>
