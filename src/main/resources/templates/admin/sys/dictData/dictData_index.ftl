<#include "../../../layout.ftl" >
<@head title="系统字典管理" />
<body>
<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-form layui-card-header layuiadmin-card-header-auto">
            <div class="layui-form-item">
                <div class="layui-inline">
                    字典标签
                </div>
                <div class="layui-inline">
                    <input type="text" name="dictLabel" id="dictLabel" autocomplete="off" class="layui-input">
                </div>
                <div class="layui-inline">
                    字典键值
                </div>
                <div class="layui-inline">
                    <input type="text" name="dictValue" id="dictValue" autocomplete="off" class="layui-input">
                </div>
                <div class="layui-inline">
                    <button class="layui-btn" lay-submit lay-filter="search_sys_dictData">
                        查询
                    </button>
                    <button class="layui-btn" lay-submit lay-filter="reset_sys_dictData">
                        重置
                    </button>
                </div>

                <div class="layui-inline">
                    当前字典类型
                </div>
                <div class="layui-inline">
                    <input type="text" id="dictType" name="dictType" value="${dictType}" readonly="readonly" autocomplete="off" class="layui-input">
                </div>
            </div>
        </div>
        <div class="layui-card-body">
            <div style="padding-bottom: 10px;text-align:right;">
                <button class="layui-btn" id="add_dictData">
                    <i class="layui-icon">&#xe608;</i> 字典数据添加
                </button>
            </div>
            <table id="tbl_sys_dictData" lay-filter="tbl_sys_dictData">

            </table>
            <script type="text/html" id="sys_dictData_toolbar">
                <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"><i
                            class="layui-icon layui-icon-edit"></i>编辑</a>

                <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i
                            class="layui-icon layui-icon-delete"></i>删除</a>
            </script>
        </div>
    </div>
</div>

<@scripts>
    <script src="${application.getContextPath()}/js/admin/sys/dictData/dictData_index.js"></script>
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

