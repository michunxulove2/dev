<#include "../../../layout.ftl" >
<@head title="新增系统字典" >
<link rel="stylesheet" href="${application.getContextPath()}/plugins/layui/css/selects-v4/formSelects-v4.css">
</@head>
<body>
	<div class="layui-form" lay-filter="layuiadmin-form-role" id="layuiadmin-form-role" style="padding: 20px 30px 0 0;">
		<div class="layui-form-item">
			<label class="layui-form-label">字典标签：</label>
			<div class="layui-input-block">
				<div class="layui-col-md12">
					<input type="text" name="dictLabel" lay-verify="required|dictName" lay-verType="tips" placeholder="请输入字典标签"
						autocomplete="off" class="layui-input" value="">
					<input type="hidden" name="dictType"  value="${dictType}">
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">字典键值：</label>
			<div class="layui-input-block">
				<div class="layui-col-md12">
					<input type="text" name="dictValue" lay-verify="required|dictType" lay-verType="tips" placeholder="请输入字典值"
						   autocomplete="off" class="layui-input" value="">
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">描述：</label>
			<div class="layui-input-block" style="width: 300px;">
				<div class="layui-col-md12">
					<textarea name="description" placeholder="请输入..." class="layui-textarea"></textarea>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">备注：</label>
			<div class="layui-input-block" style="width: 300px;">
				<div class="layui-col-md12">
					<textarea name="remarks" placeholder="请输入..." class="layui-textarea"></textarea>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<button class="layui-btn layui-hide" lay-submit lay-filter="add-dictData-submit" id="add-dictData-submit">提交</button>
		</div>
	</div>
<@scripts>
	<script src="${application.getContextPath()}/js/admin/sys/dictType/dictType_add.js"></script>
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