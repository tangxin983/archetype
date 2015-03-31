<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>角色管理</title>
<%@ include file="/WEB-INF/views/include/treeview.jsp"%>
<script type="text/javascript">
	var tree;

	function formSubmit(){
		var ids = [], nodes = tree.getCheckedNodes(true);
		for (var i = 0; i < nodes.length; i++) {
			ids.push(nodes[i].id);
		}
		$("#resourceIds").val(ids);
		$("#inputForm").submit();
	}

	$(document).ready(function() {
		var setting = {
			check : {
				enable : true,
				nocheckInherit : true
			},
			view : {
				selectedMulti : false
			},
			data : {
				simpleData : {
					enable : true
				}
			},
			callback : {
				beforeClick : function(id, node) {
					tree.checkNode(node, !node.checked, true, true);
					return false;
				}
			}
		};
		$.get("${ctx}/sys/resource/treeData", function(zNodes){
			// 初始化树结构
			tree = $.fn.zTree.init($("#resourceTree"), setting, zNodes);
			// 默认展开全部节点
			tree.expandAll(true);
			// 默认选择节点
			<c:forEach var="resourceId" items="${entity.resourceIds}">
			var node = tree.getNodeByParam("id", "${resourceId}");
			tree.checkNode(node, true, false);
			</c:forEach>
		});
	});
</script>
</head>
<body>
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="text-muted bootstrap-admin-box-title">角色编辑</div>
		</div>
		<div class="panel-body">
			<form class="form-horizontal" id="inputForm"
				action="${ctxModule}/save" method="post">
				<input type="hidden" name="id" value="${entity.id}">
				<div class="form-group">
					<label class="col-md-2 control-label">角色名称:</label>
					<div class="col-md-6">
						<input id="roleName" name="roleName" maxlength="50" class="form-control required"
							value="${entity.roleName}" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label">角色描述:</label>
					<div class="col-md-6">
						<input id="description" name="description" maxlength="50" class="form-control required"
							value="${entity.description}" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label">角色授权:</label>
					<div class="col-md-6">
						<div id="resourceTree" class="ztree"
							style="margin-top: 3px; float: left;"></div>
						<input type="hidden" id="resourceIds" name="resourceIds"
							value="${entity.resourceIds}">
					</div>
				</div>
				<div class="form-group">
					<div class="col-md-offset-2 col-md-10">
						<a onclick="formSubmit();" class="btn btn-primary">保存</a>
						<a href="${ctxModule}" class="btn btn-default">返 回</a>
					</div>
				</div>
			</form>
		</div>
	</div>

</body>
</html>