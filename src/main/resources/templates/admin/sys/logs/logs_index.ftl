<#include "../../../layout.ftl" >
<@head title="系统用户管理" />
<body>
<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-form layui-card-header layuiadmin-card-header-auto">
            <div class="layui-form-item">
                <div class="layui-inline">
                    操作人
                </div>
                <div class="layui-inline">
                    <input type="text" name="userName" autocomplete="off" class="layui-input">
                </div>
                <div class="layui-inline">
                    <button class="layui-btn" lay-submit lay-filter="search_sys_logs">
                        查询
                    </button>
                </div>
            </div>
        </div>
        <div class="layui-card-body">
            <table id="tbl_sys_logs" lay-filter="tbl_sys_logs">

            </table>
<#--            <script type="text/html" id="sys_logs_toolbar">-->
<#--                <a class="layui-btn layui-btn-xs" lay-event="view"><i class="layui-icon layui-icon-search"></i>查看</a>-->
<#--                <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"><i-->
<#--                            class="layui-icon layui-icon-edit"></i>编辑</a>-->
<#--                <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i-->
<#--                            class="layui-icon layui-icon-delete"></i>删除</a>-->
<#--            </script>-->
        </div>
    </div>
</div>

<@scripts>
    <script src="${application.getContextPath()}/js/admin/sys/logs/logs_index.js"></script>
    <script>
        layui.config({
            base: '${application.getContextPath()}/plugins/layui/' //静态资源所在路径
        }).extend({
            index: 'lib/index' //主入口模块
        }).use(['util', 'index', 'table'], callback);
    </script>
</@scripts>
</body>
</html>

