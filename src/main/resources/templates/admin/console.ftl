<#include "../layout.ftl" >
<@head title="新增系统用户" >
    <link rel="stylesheet" href="${application.getContextPath()}/plugins/layui/css/selects-v4/formSelects-v4.css">
</@head>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-row layui-col-space15">
                <div class="layui-col-md6">
                    <div class="layui-card">
                        <div class="layui-card-header">商铺区域统计饼状图</div>
                        <div class="layui-card-body">
                            <div class="layadmin-dataview">
                                <div carousel-item id="dataByarea" style="height: 100%">

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
<#--                <div class="layui-col-md6">-->
<#--                    <div class="layui-card">-->
<#--                        <div class="layui-card-header">商铺违规统计饼状图</div>-->
<#--                        <div class="layui-card-body">-->
<#--                            <div class="layadmin-dataview">-->
<#--                                <div carousel-item id="dataByRules" style="height: 100%">-->

<#--                                </div>-->
<#--                            </div>-->
<#--                        </div>-->
<#--                    </div>-->
<#--                </div>-->
            </div>
        </div>

    </div>
</div>
<@scripts>
    <script>
        layui.config({
            base: '${application.getContextPath()}/plugins/layui/' //静态资源所在路径
        }).extend({
            index: 'lib/index' //主入口模块
        }).use(['util', 'index', 'table', 'console']);
    </script>
</@scripts>
</body>
</html>

