 function callback(){
    var $ = layui.$;
    var form = layui.form;
    var table = layui.table;
    var util = layui.util;
    table.render({
		elem: "#tbl_sys_dictData",
		url: _contextPath + "admin/sys/dictData/dictData_data_list?dictType="+$("#dictType").val(),
		height: 'full-180',
		limit: 20,
		page: true,
		cols: [
			[{
				field: "dictLabel",
				title: "字典标签"
			},{
				field: "dictValue",
				title: "字典键值"
			},{
				field: "dictType",
				title: "字典类型"
			},{
				field: "description",
				title: "描述"
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
				toolbar: "#sys_dictData_toolbar"
			}]
		],
		text: {
			none: '暂无数据!'
		},
		request: {
			limitName: 'pageSize'
		  }
	});
     form.on('submit(search_sys_dictData)', function(data){
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
    table.on("tool(tbl_sys_dictData)", function(e) {
		e.data;
		if ("del" === e.event) layer.confirm("确定删除该字典数据？", function(_$) {
			$.ajax({ url: _contextPath + "admin/sys/dictData/dictData_del?id=" +e.data.id, success: function(result){
				if(result.code == 0){
					layer.msg('操作成功!', {icon: 6});
					table.reload("tbl_sys_dictData");
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
                title: "编辑字典数据",
                content: _contextPath + "admin/sys/dictData/dictData_edit?id=" +e.data.id,
                area: ["600px", "600px"],
                btn: ["确定", "取消"],
                yes: function(index, _dom) {
                    var iframe = window["layui-layer-iframe" + index];
                    var _submit = _dom.find("iframe").contents().find("#add-dictData-submit");
                    iframe.layui.form.on("submit(add-dictData-submit)", function(_dom) {
                        $.ajax({ url: _contextPath + "admin/sys/dictData/dictData_edit",type: 'POST', data: _dom.field, success: function(result){
                                if(result.code == 0){
                                    layer.msg('操作成功!', {icon: 6});
                                    table.reload("tbl_sys_dictData");
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

    $('#add_dictData').on('click', function(){
    	layer.open({
    		type: 2
    		,title: '添加字典数据('+$("#dictType").val()+')'
    		,content: _contextPath + 'admin/sys/dictData/dictData_add?dictType='+$("#dictType").val()
            ,area: ['600px', '600px']
            ,btn: ['确定', '取消']
            ,yes: function(index, _dom){
              var iframeWindow = window['layui-layer-iframe'+ index];
              var _submit = _dom.find('iframe').contents().find("#add-dictData-submit");

              //监听提交
              iframeWindow.layui.form.on('submit(add-dictData-submit)', function(_dom){
            	  $.ajax({ url: _contextPath + "admin/sys/dictData/dictData_add",type: 'POST', data: _dom.field, success: function(result){
						if(result.code == 0){
							layer.msg('操作成功!', {icon: 6});
							table.reload("tbl_sys_dictData");
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