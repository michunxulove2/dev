<#include "../../../layout.ftl" >
<@head title="机构管理" />
<body>
	<div class="layui-fluid">   
		<div class="layui-card">
			<div class="layui-card-body">
			<#list Session.loginUser.resList as sysRes>
				<#if sysRes.pId?? && (sysRes.pId == (Request.viewId)!"") && sysRes.perms == "add">
			        <div style="padding-bottom: 10px;text-align:right;">
						<button class="layui-btn" id="add_org">
							<i class="layui-icon">&#xe608;</i> 添加
						</button>
			        </div>
		        </#if>
		    </#list>
		        <table id="tbl_sys_org" lay-filter="tbl_sys_org">
		        	
		        </table>  
		        <script type="text/html" id="sys_org_toolbar">
		        	<#list Session.loginUser.resList as sysRes>
		        		<#if sysRes.pId?? && (sysRes.pId == (Request.viewId)!"") && sysRes.perms == "update">
		          			<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>编辑</a>
		          		</#if>
		          		<#if sysRes.pId?? && (sysRes.pId == (Request.viewId)!"") && sysRes.perms == "delete">
		          			<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i class="layui-icon layui-icon-delete"></i>删除</a>
		          		</#if>
		          	</#list>
		        </script>
			</div>
		</div>
	</div>

<@scripts>
	<script src="${application.getContextPath()}/js/admin/sys/org/org_index.js"></script>
	<script>
		layui.config({
			base: '${application.getContextPath()}/plugins/layui/' //静态资源所在路径
		}).extend({
			index: 'lib/index' //主入口模块
		}).use(['util','index','table'], callback);
	</script>
</@scripts>
</body>
</html>

