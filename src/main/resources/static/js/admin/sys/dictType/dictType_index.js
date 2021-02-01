 function callback(){
    var $ = layui.$;
    var form = layui.form;
    var table = layui.table;
    var util = layui.util;
    table.render({
		elem: "#tbl_sys_dictType",
		url: _contextPath + "admin/sys/dictType/dictType_data_list",
		height: 'full-180',
		limit: 20,
		page: true,
		cols: [
			[{
				field: "dictName",
				title: "字典名称"
			},{
				field: "dictType",
				title: "字典类型"
			},{
				field: "isSys",
				title: "系统字典",
				templet: function(data){
					if(data.isSys == 0){
						return '否';
					}else{
						return '是';
					}
				}
			},{
				field: "createDate",
				title: "创建时间",
				width: 175,
				templet: function(data){
			        return util.toDateString(data.createDate,_date_format);
			      }
			},{
				field: "remarks",
				title: "备注"
			},{
				title: "操作",
				width: 250,
				align: "center",
				fixed: "right",
				toolbar: "#sys_dictType_toolbar"
			}]
		],
		text: {
			none: '暂无数据!'
		},
		request: {
			limitName: 'pageSize'
		  }
	});
     form.on('submit(search_sys_dictType)', function(data){
         table.reload('tbl_sys_dictType', {
             url: _contextPath + 'admin/sys/dictType/dictType_data_list',
             where: data.field,
             page: {
                 curr: 1 //重新从第 1 页开始
             }
         });
     });
	 form.on('submit(reset_sys_dictType)', function(data){
		 $("#dictName").val("");
		 $("#dictType").val("");
		 data.field = "";
		 table.reload('tbl_sys_dictType', {
			 url: _contextPath + 'admin/sys/dictType/dictType_data_list',
			 where: data.field,
			 page: {
				 curr: 1 //重新从第 1 页开始
			 }
		 });
	 });
    table.on("tool(tbl_sys_dictType)", function(e) {
		e.data;
		if ("del" === e.event) layer.confirm("执行该操作会同时删除该字典类型下的所有字典数据,确定删除吗？", function(_$) {
			$.ajax({ url: _contextPath + "admin/sys/dictType/dictType_del?id=" +e.data.id, success: function(result){
				if(result.code == 0){
					layer.msg('操作成功!', {icon: 6});
					table.reload("tbl_sys_dictType");
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
                title: "编辑字典类型",
                content: _contextPath + "admin/sys/dictType/dictType_edit?id=" +e.data.id,
                area: ["600px", "600px"],
                btn: ["确定", "取消"],
                yes: function(index, _dom) {
                    var iframe = window["layui-layer-iframe" + index];
                    var _submit = _dom.find("iframe").contents().find("#add-dictType-submit");
                    iframe.layui.form.on("submit(add-dictType-submit)", function(_dom) {
                        $.ajax({ url: _contextPath + "admin/sys/dictType/dictType_edit",type: 'POST', data: _dom.field, success: function(result){
                                if(result.code == 0){
                                    layer.msg('操作成功!', {icon: 6});
                                    table.reload("tbl_sys_dictType");
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
			window.location.href = "/admin/sys/dictData/dictData_index?id=" +e.data.id;
		}
	});

    $('#add_dictType').on('click', function(){
    	layer.open({
    		type: 2
    		,title: '添加系统字典'
    		,content: _contextPath + 'admin/sys/dictType/dictType_add'
            ,area: ['600px', '600px']
            ,btn: ['确定', '取消']
            ,yes: function(index, _dom){
              var iframeWindow = window['layui-layer-iframe'+ index];
              var _submit = _dom.find('iframe').contents().find("#add-dictType-submit");

              //监听提交
              iframeWindow.layui.form.on('submit(add-dictType-submit)', function(_dom){
            	  $.ajax({ url: _contextPath + "admin/sys/dictType/dictType_add",type: 'POST', data: _dom.field, success: function(result){
						if(result.code == 0){
							layer.msg('操作成功!', {icon: 6});
							table.reload("tbl_sys_dictType");
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