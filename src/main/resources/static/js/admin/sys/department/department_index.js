 function callback(){
    var $ = layui.$;
    var form = layui.form;
    var table = layui.table;
    var util = layui.util;
    
    table.render({
		elem: "#tbl_sys_department",
		url: _contextPath + "admin/sys/department/department_data_list",
		height: 'full-180',
		limit: 20,
		page: true,
		cols: [
			[{
				field: "id",
				title: "部门编码",
			},{
				field: "name",
				title: "部门名称"
			},{
				field: "hasEnabled",
				title: "是否启用",
				align: 'center',
				width: 100,
				templet: function(data){
					if(data.hasEnabled == 'N'){
						return '<button class="layui-btn layui-btn-disabled layui-btn-xs">已停用</button>';
					}else{
						return '<button class="layui-btn layui-btn-primary layui-btn-xs">使用中</button>';
					}
				}
			}, {
				field: "createTime",
				title: "创建时间",
				width: 175,
				templet: function(data){
					return util.toDateString(data.createTime,_date_format);
			      }
			},{
				title: "操作",
				width: 250,
				align: "center",
				fixed: "right",
				toolbar: "#sys_department_toolbar"
			}]
		],
		text: {
			none: '暂无数据!'
		},
		request: {
			limitName: 'pageSize'
		  }
	});
     form.on('submit(search_sys_department)', function(data){
         table.reload('tbl_sys_department', {
             url: _contextPath + 'admin/sys/department/department_data_list',
             where: data.field,
             page: {
                 curr: 1 //重新从第 1 页开始
             }
         });
     });
    table.on("tool(tbl_sys_department)", function(e) {
		e.data;
		if ("del" === e.event) layer.confirm("确定删除该部门？", function(_$) {
			$.ajax({ url: _contextPath + "admin/sys/department/department_del?id=" +e.data.id, success: function(result){
				if(result.code == 0){
					layer.msg('操作成功!', {icon: 6});
					table.reload("tbl_sys_department");
				}else{
					layer.msg(result.msg, {icon: 5});
				}

		    }});
			layer.close(_$);
		});
		else if ("edit" === e.event) {
            $(e.tr);
            layer.open({
                type: 2,
                title: "编辑部门信息",
                content: _contextPath + "admin/sys/department/department_edit?id=" +e.data.id,
                area: ["600px", "600px"],
                btn: ["确定", "取消"],
                yes: function(index, _dom) {
                    var iframe = window["layui-layer-iframe" + index];
                    var _submit = _dom.find("iframe").contents().find("#add-sysDepartment-submit");
                    iframe.layui.form.on("submit(add-sysDepartment-submit)", function(_dom) {
                        $.ajax({ url: _contextPath + "admin/sys/department/department_edit",type: 'POST', data: _dom.field, success: function(result){
                                if(result.code == 0){
                                    layer.msg('操作成功!', {icon: 6});
                                    table.reload("tbl_sys_department");
                                    layer.close(index);
                                }else{
                                    layer.msg(result.msg, {icon: 5});
                                }
                            }});
                    });
                    _submit.trigger("click");
                },
                success: function(index, _dom) {}
            })
		}
		else if("view" === e.event){
            $(e.tr);
            layer.open({
                type: 2,
                title: "查看部门信息",
                content: _contextPath + "admin/sys/department/department_view?id=" +e.data.id,
                area: ["600px", "600px"],
                btn: ["关闭"],
                yes: function(index) {
                    layer.close(index);
                }
            })
		}
	});
  
    $('#add_department').on('click', function(){
    	layer.open({
    		type: 2
    		,title: '添加部门'
    		,content: _contextPath + 'admin/sys/department/department_add'
            ,area: ['600px', '600px']
            ,btn: ['确定', '取消']
            ,yes: function(index, _dom){
              var iframeWindow = window['layui-layer-iframe'+ index];
              var _submit = _dom.find('iframe').contents().find("#add-sysDepartment-submit");

              //监听提交
              iframeWindow.layui.form.on('submit(add-sysDepartment-submit)', function(_dom){
            	  $.ajax({ url: _contextPath + "admin/sys/department/department_add",type: 'POST', data: _dom.field, success: function(result){
						if(result.code == 0){
							layer.msg('操作成功!', {icon: 6});
							table.reload("tbl_sys_department");
							layer.close(index);
						}else{
							layer.msg(result.msg, {icon: 5});
						}
				  }});
              });  
              
              _submit.trigger('click');
            }
          }); 
    });
    
}