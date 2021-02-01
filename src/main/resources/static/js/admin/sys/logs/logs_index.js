function callback() {
    var $ = layui.$;
    var form = layui.form;
    var table = layui.table;
    var util = layui.util;

    table.render({
        elem: "#tbl_sys_logs",
        url: _contextPath + "admin/sys/logs/logs_data_list",
        height: 'full-180',
        limit: 20,
        page: true,
        cols: [
            [{
                field: "userName",
                title: "操作人"
            }, {
                field: "operation",
                title: "执行操作",
            }, {
                field: "method",
                title: "执行方法",
            }, {
                field: "ip",
                title: "IP地址",
            }, {
                field: "createDate",
                title: "创建时间",
                width: 175,
                templet: function (data) {
                    return util.toDateString(data.createDate, _date_format);
                }
            },
            //     {
            //     title: "操作",
            //     width: 250,
            //     align: "center",
            //     fixed: "right",
            //     toolbar: "#sys_logs_toolbar"
            // }
            ]
        ],
        text: {
            none: '暂无数据!'
        },
        request: {
            limitName: 'pageSize'
        }
    });
    form.on('submit(search_sys_logs)', function (data) {
        table.reload('tbl_sys_logs', {
            url: _contextPath + 'admin/sys/logs/logs_data_list',
            where: data.field,
            page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    });
}