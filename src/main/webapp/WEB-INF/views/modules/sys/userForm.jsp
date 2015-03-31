<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>用户管理</title>
<script type="text/javascript">
	$(document).ready(function() {

	});
</script>
</head>
<body>
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="text-muted bootstrap-admin-box-title">用户编辑</div>
		</div>
		<div class="panel-body">
			<form class="form-horizontal" id="inputForm"
				action="${ctxModule}/save" method="post">
				<input type="hidden" name="id" value="${entity.id}">
				<div class="form-group">
					<label class="col-md-2 control-label">登录名:</label>
					<div class="col-md-6">
						<input id="loginName" name="loginName" maxlength="50"
							class="form-control required" value="${entity.loginName}" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label">姓名:</label>
					<div class="col-md-6">
						<input name="userName" maxlength="50"
							class="form-control required" value="${entity.userName}" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label">密码:</label>
					<div class="col-md-6">
						<input type="password" id="password" name="password"
							${not empty entity.id?"placeholder='不变请留空'":""} maxlength="50"
							minlength="6"
							class="form-control ${empty entity.id?'required':''}" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label">确认密码:</label>
					<div class="col-md-6">
						<input type="password" id="confirmPassword" name="confirmPassword"
							class="form-control" equalTo="#password" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label">邮箱:</label>
					<div class="col-md-6">
						<input name="email" maxlength="100" class="form-control email"
							value="${entity.email}" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label">手机:</label>
					<div class="col-md-6">
						<input name="mobile" maxlength="100" class="form-control"
							value="${entity.mobile}" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label">用户角色:</label>
					<div class="col-md-6">
						<c:forEach items="${roles}" var="role">
							<label class="checkbox-inline">
							  	<input type="checkbox" name="roleIds" id="roleIds" value="${role.id}" ${fns:contains(entity.roleIds, role.id) ? "checked" : ""}> ${role.description}
							</label>
						</c:forEach>
					</div>
				</div>
				<div class="form-group">
					<div class="col-md-offset-2 col-md-10">
						<input type="submit" class="btn btn-primary" value="保存" /> <a
							href="${ctxModule}" class="btn btn-default">返 回</a>
					</div>
				</div>
			</form>
		</div>
	</div>

</body>
</html>