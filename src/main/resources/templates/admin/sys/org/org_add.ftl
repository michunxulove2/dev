<#include "../../../layout.ftl" >
<@head title="新增机构" >
<meta name="viewport" content="initial-scale=1.0, user-scalable=no"> 
</@head>
<body>
	<div class="layui-form" lay-filter="layuiadmin-form-role" id="layuiadmin-form-role" style="padding: 20px 30px 0 0;">
		<div class="layui-form-item">
			<label class="layui-form-label">机构名称：</label>
			<div class="layui-input-block">
				<div class="layui-col-md12">
					<input type="text" name="lng" id="lng" autocomplete="off" class="layui-input layui-hide" value="">
					<input type="text" name="lat" id="lat" autocomplete="off" class="layui-input layui-hide" value="">
					<input type="text" name="orgName" lay-verify="required" lay-verType="tips" placeholder="请输入机构名称" 
						autocomplete="off" class="layui-input" value="">
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">管理员：</label>
			<div class="layui-input-block">
				<div class="layui-col-md12">
					<input type="text" name="loginName" lay-verify="required" placeholder="请输入管理员登陆账号" 
						autocomplete="off" class="layui-input" value="">
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">选择权限：</label>
			<div class="layui-input-block">
				<div id="select_res" lay-filter="select_res"></div>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">联系人：</label>
			<div class="layui-input-block">
				<div class="layui-col-md12">
					<input type="text" name="contactName" placeholder="请输入联系人姓名" 
						autocomplete="off" class="layui-input" value="">
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">联系方式：</label>
			<div class="layui-input-block">
				<div class="layui-col-md12">
					<input type="text" name="contactPhone" placeholder="请输入联系方式" 
						autocomplete="off" class="layui-input" value="">
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">地址：</label>
			<div class="layui-input-block">
				<div class="layui-col-md12">
					<input type="text" name="address" id="address" placeholder="请拖动地图选择地址"
						autocomplete="off" class="layui-input" value="">
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div style="width:555px;height:250px;margin-left:20px;border:1px solid #e6e6e6" id="org_map"></div>
		</div>
		<div class="layui-form-item">
			<button class="layui-btn layui-hide" lay-submit lay-filter="add-sysOrg-submit" id="add-sysOrg-submit">提交</button>
		</div>
	</div>

<@scripts>
	<script type="text/javascript" src="https://webapi.amap.com/maps?v=1.4.10&key=489ed21fd4d851e25c64ecb0d0bec2c5"></script> 
	<script src="${application.getContextPath()}/js/gaode/ui.js"></script>
	<script src="${application.getContextPath()}/js/admin/sys/org/org_add.js"></script>
	<script>
		layui.config({
			base: '${application.getContextPath()}/plugins/layui/' //静态资源所在路径
		}).extend({
			index: 'lib/index' //主入口模块
		}).use(['index','form','authtree'], callback);
	</script>
</@scripts>
</body>
</html>