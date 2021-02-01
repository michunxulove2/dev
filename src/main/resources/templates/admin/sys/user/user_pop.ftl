<#include "../../../layout.ftl" >
<@head title="系统用户管理" />
<body>
<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-form layui-card-header layuiadmin-card-header-auto">
            <div class="layui-form-item">
                <div class="layui-inline">
                    姓名
                </div>
                <div class="layui-inline">
                    <input type="text" name="nickname" autocomplete="off" class="layui-input">
                </div>
                <div class="layui-inline">
                    手机号
                </div>
                <div class="layui-inline">
                    <input type="text" name="contactPhone" autocomplete="off" class="layui-input">
                </div>
                <div class="layui-inline">
                    <button class="layui-btn" lay-submit lay-filter="search_sys_user">
                        查询
                    </button>
                </div>
            </div>
        </div>
        <div class="layui-card-body">
            <#list Session.loginUser.resList as sysRes>
                <#if sysRes.pId?? && (sysRes.pId == (Request.viewId)!"") && sysRes.perms == "add">
                    <div style="padding-bottom: 10px;text-align:right;">
                        <button class="layui-btn" id="add_user">
                            <i class="layui-icon">&#xe608;</i> 添加
                        </button>
                    </div>
                </#if>
            </#list>
            <table id="tbl_sys_user" lay-filter="tbl_sys_user">

            </table>
        </div>
        <div class="layui-form-item">
            <div class="layui-layer-btn layui-layer-btn-">
                <a class="layui-layer-btn0" onclick="chooseUser()">确定</a>
                <a class="layui-layer-btn1" onclick="closeIframe()">关闭</a>
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
        }).use(['util', 'index', 'table'], function () {
            var $ = layui.$;
            var form = layui.form;
            var table = layui.table;
            var util = layui.util;
            table.render({
                elem: "#tbl_sys_user",
                url: _contextPath + "admin/sys/user/user_data_list",
                height: 'full-180',
                limit: 20,
                page: true,
                cols: [
                    [{type: 'checkbox'},
                        {
                            field: "userId",
                            title: "userId",
                            hide: true
                        }, {
                        field: "loginName",
                        title: "登录名"
                    }, {
                        field: "nickname",
                        title: "姓名"
                    }, {
                        field: "contactPhone",
                        title: "手机号"
                    }, {
                        field: "jobNames",
                        title: "岗位11"
                    }, {
                        field: "departmentName",
                        title: "所属部门"
                    }, {
                        field: "sex",
                        title: "性别",
                        align: 'center',
                        templet: function (data) {
                            if (data.sex == 0) {
                                return '女';
                            } else {
                                return '男';
                            }
                        }
                    }, {
                        field: "email",
                        title: "邮箱"
                    }, {
                        field: "isEnabled",
                        title: "是否启用",
                        align: 'center',
                        width: 100,
                        templet: function (data) {
                            if (data.hasEnabled == 'N') {
                                return '<button class="layui-btn layui-btn-disabled layui-btn-xs">已停用</button>';
                            } else {
                                return '<button class="layui-btn layui-btn-primary layui-btn-xs">使用中</button>';
                            }
                        }
                    }, {
                        field: "createTime",
                        title: "创建时间",
                        width: 175,
                        templet: function (data) {
                            return util.toDateString(data.createTime, _date_format);
                        }
                    }, {
                        title: "操作",
                        width: 250,
                        align: "center",
                        fixed: "right",
                        toolbar: "#sys_user_toolbar"
                    }]
                ],
                text: {
                    none: '暂无数据!'
                },
                request: {
                    limitName: 'pageSize'
                }
            });
            form.on('submit(search_sys_user)', function (data) {
                table.reload('tbl_sys_user', {
                    url: _contextPath + 'admin/sys/user/user_data_list',
                    where: data.field,
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
            });
            var index = parent.layer.getFrameIndex(window.name);
            chooseUser = function () {
                var checkStatus = table.checkStatus('tbl_sys_user');
                var data = checkStatus.data;
                var userIdStr = "";
                var userNameStr = "";
                for (var i = 0; i < data.length; i++) {
                    userIdStr += data[i].userId + ",";
                    userNameStr += data[i].nickname + ",";
                }
                userNameStr = userNameStr.substring(0,userNameStr.length-1);
                userIdStr = userIdStr.substring(0,userIdStr.length-1);
                parent.layui.$("#userName").val(userNameStr);
                parent.layui.$("#userId").val(userIdStr);
                parent.layer.close(index);
            };
            closeIframe = function () {
                parent.layer.close(index);
            }
        });
    </script>
</@scripts>
</body>
</html>

