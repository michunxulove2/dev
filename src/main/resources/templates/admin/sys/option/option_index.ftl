<#include "../../../layout.ftl" >
<@head title="系统配置项" />
<body>
<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-form layui-card-header layuiadmin-card-header-auto">
            <div class="layui-tab layui-tab-brief">
                <ul class="layui-tab-title">
                    <li class="layui-this">平台参数</li>
                    <li>打印设置</li>
                </ul>
                <div class="layui-tab-content">

                    <div class="layui-tab-item layui-show">
                        <blockquote class="layui-elem-quote">平台参数配置</blockquote>
                        <div class="layui-form" lay-filter="layuiadmin-form-role" id="layuiadmin-form-role"
                             style="padding: 20px 30px 0 0;">
                            <div class="layui-form-item">
                                <label class="layui-form-label">平台名称</label>
                                <div class="layui-input-block">
                                    <div class="layui-col-md12">
                                        <input type="text" id="name" class="layui-input inputDisabled">
                                    </div>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">支付域名</label>
                                <div class="layui-input-block">
                                    <div class="layui-col-md12">
                                        <input type="text" id="pay_url" class="layui-input inputDisabled">
                                    </div>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">IMEI启用</label>
                                <div class="layui-input-block">
                                    <div class="layui-col-md12" id="template">
                                        <input type="checkbox" id="imei" lay-filter="switchIMEI" lay-skin="switch"
                                               lay-text="开启|关闭">
                                    </div>
                                    <input type="hidden" id="switch_value"/>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <div class="layui-layer-btn layui-layer-btn-">
                                    <a class="layui-layer-btn0" onclick="saveSysOption()">确定</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="layui-tab-item">
                        <blockquote class="layui-elem-quote">打印配置</blockquote>
                        <div class="layui-form" lay-filter="layuiadmin-form-role" id="layuiadmin-form-role"
                             style="padding: 20px 30px 0 0;">
                            <div class="layui-form-item">
                                <label class="layui-form-label">发票抬头</label>
                                <div class="layui-input-block">
                                    <div class="layui-col-md12">
                                        <input type="text" id="title" class="layui-input inputDisabled">
                                    </div>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">管理员微信</label>
                                <div class="layui-input-block">
                                    <div class="layui-col-md12">
                                        <input type="text" id="weixin" class="layui-input inputDisabled">
                                    </div>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <div class="layui-layer-btn layui-layer-btn-">
                                    <a class="layui-layer-btn0" onclick="savePrintOption()">确定</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<@scripts>
    <script>
        var obj = ${(data)!};
        var printValue = ${(printValue)!};
        layui.config({
            base: '${application.getContextPath()}/plugins/layui/' //静态资源所在路径
        }).extend({
            index: 'lib/index' //主入口模块
        }).use(['util', 'index', 'table'], function () {
            var $ = layui.$;
            var form = layui.form;
            if (obj) {
                $("#name").val(obj.name);
                $("#pay_url").val(obj.payUrl);
                var html = "<input type=\"checkbox\" id=\"imei\" lay-filter=\"switchIMEI\" lay-skin=\"switch\" lay-text=\"开启|关闭\" checked>";
                if (obj.imei == true || obj.imei == 'true') {
                    $("#template").html(html);
                } else {
                    $("#template").html("<input type=\"checkbox\" id=\"imei\" lay-filter=\"switchIMEI\" lay-skin=\"switch\" lay-text=\"开启|关闭\">");
                }
                form.render();
            }
            if(printValue){
                $("#title").val(printValue.title);
                $("#weixin").val(printValue.weixin);
            }
            form.on('switch(switchIMEI)', function (data) {
                //开关是否开启，true或者false
                var checked = data.elem.checked;
                $("#switch_value").val(checked);
            });
            saveSysOption = function () {
                var data = {
                    'groupName':'sys_group',
                    'name': $("#name").val(),
                    'payUrl': $("#pay_url").val(),
                    'imei': $("#switch_value").val()
                };
                $.ajax({
                    url: _contextPath + "admin/sys/option/option_add",
                    type: 'POST',
                    data: data,
                    success: function (result) {
                        if (result.code == 0) {
                            layer.msg('操作成功!', {icon: 6});
                        } else {
                            layer.msg(result.msg, {icon: 5});
                        }
                    }
                });
            }
            savePrintOption = function () {
                var data = {
                    'groupName':'print_group',
                    'title': $("#title").val(),
                    'weixin': $("#weixin").val(),
                };
                $.ajax({
                    url: _contextPath + "admin/sys/option/option_add_print",
                    type: 'POST',
                    data: data,
                    success: function (result) {
                        if (result.code == 0) {
                            layer.msg('操作成功!', {icon: 6});
                        } else {
                            layer.msg(result.msg, {icon: 5});
                        }
                    }
                });
            }
        });
    </script>
</@scripts>
</body>
</html>

