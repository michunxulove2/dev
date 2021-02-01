<#include "../../../layout.ftl" >
<@head title="新增系统用户" >
<link rel="stylesheet" href="${application.getContextPath()}/plugins/layui/css/selects-v4/formSelects-v4.css">
</@head>
<body>
	<div class="layui-form" lay-filter="layuiadmin-form-role" id="layuiadmin-form-role" style="padding: 20px 30px 0 0;">
		<div class="layui-form-item">
			<label class="layui-form-label">部门名称：</label>
			<div class="layui-input-block">
				<div class="layui-col-md12">
					<input type="text" name="name" lay-verify="name" placeholder="请输入部门名称"
						autocomplete="off" lay-verType="tips" class="layui-input" value="">
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">是否启用：</label>
			<div class="layui-input-block">
				<input type="radio" name="hasEnabled" value="Y" title="启用"  checked>
				<input type="radio" name="hasEnabled" value="N" title="禁用" >
			</div>
		</div>
		<div class="layui-form-item">
			<button class="layui-btn layui-hide" lay-submit lay-filter="add-sysDepartment-submit" id="add-sysDepartment-submit">提交</button>
		</div>
	</div>
<@scripts>
	<script src="${application.getContextPath()}/js/admin/sys/department/department_add.js"></script>
	<script>
		layui.config({
			base: '${application.getContextPath()}/plugins/layui/' //静态资源所在路径
		}).extend({
			index: 'lib/index', //主入口模块
			formSelects: 'formSelects-v4'
		}).use(['index', 'form','formSelects'], callback);
	</script>
</@scripts>
</body>
</html>