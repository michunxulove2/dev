<#include "../../../layout.ftl" >
<@head title="系统字典管理" />
<body>
<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-form layui-card-header layuiadmin-card-header-auto">
            <div class="layui-form-item">
                <div class="layui-inline">
                    字典名称
                </div>
                <div class="layui-inline">
                    <input type="text" name="dictName" id="dictName" autocomplete="off" class="layui-input">
                </div>
                <div class="layui-inline">
                    字典类型
                </div>
                <div class="layui-inline">
                    <input type="text" name="dictType" id="dictType" autocomplete="off" class="layui-input">
                </div>
                <div class="layui-inline">
                    <button class="layui-btn" lay-submit lay-filter="search_sys_dictType">
                        查询
                    </button>
                    <button class="layui-btn" lay-submit lay-filter="reset_sys_dictType">
                        重置
                    </button>
                </div>
            </div>
        </div>
        <div class="layui-card-body">
            <div style="padding-bottom: 10px;text-align:right;">
                <button class="layui-btn" id="add_dictType">
                    <i class="layui-icon">&#xe608;</i> 字典添加
                </button>
            </div>
            <table id="tbl_sys_dictType" lay-filter="tbl_sys_dictType">

            </table>
            <script type="text/html" id="sys_dictType_toolbar">
                <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"><i
                            class="layui-icon layui-icon-edit"></i>编辑</a>

                <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i
                            class="layui-icon layui-icon-delete"></i>删除</a>

                <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="view"><i
                            class="layui-icon"></i>字典数据</a>
            </script>
        </div>
    </div>
</div>

<@scripts>
    <script src="${application.getContextPath()}/js/admin/sys/dictType/dictType_index.js"></script>
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

