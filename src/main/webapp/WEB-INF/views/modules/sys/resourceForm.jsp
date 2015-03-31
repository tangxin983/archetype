<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>资源管理</title>
<script type="text/javascript">
	$(document).ready(function() {
		 
	});
</script>
</head>
<body>
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="text-muted bootstrap-admin-box-title">资源编辑</div>
		</div>
		<div class="panel-body">
			<form class="form-horizontal" id="inputForm" action="${ctxModule}/save" method="post">
				<input type="hidden" name="id" value="${entity.id}">
				<div class="form-group">
					<label class="col-md-2 control-label">上级资源:</label>
					<div class="col-md-6">
						<tags:treeselect id="menu" name="parentId"
							value="${entity.parent.id}" labelName="parentName"
							labelValue="${entity.parent.resourceName}" allowClear="false" title="上级资源"
							url="/sys/resource/treeData" extId="${entity.id}" cssClass="required" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label">资源名称:</label>
					<div class="col-md-6">
						<input name="resourceName" maxlength="50" class="form-control required"
							value="${entity.resourceName}" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label">资源描述:</label>
					<div class="col-md-6">
						<input name="description" maxlength="50" class="form-control"
							value="${entity.description}" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label">链接:</label>
					<div class="col-md-6">
						<input name="href" maxlength="200" class="form-control"
							value="${entity.href}" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label">排序:</label>
					<div class="col-md-6">
						<input name="sort" maxlength="10"
							class="form-control required digits" value="${entity.sort}" />
					</div>
				</div>
				<div class="form-group">
					<div class="col-md-offset-2 col-md-10">
						<input type="submit" class="btn btn-primary" value="保存" /> 
						<a href="${ctxModule}" class="btn btn-default">返 回</a>
					</div>
				</div>
			</form>
		</div>
	</div>

</body>
</html>