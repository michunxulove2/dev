 function callback(){
    var $ = layui.$;
    var form = layui.form;
    var table = layui.table;
    var util = layui.util;
    
    table.render({
		elem: "#tbl_sys_org",
		url: _contextPath + "admin/sys/org/org_data_list",
		height: 'full-180',
		limit: 20,
		page: true,
		cols: [
			[{
				field: "orgId",
				title: "orgId",
				hide: true
			},{
				field: "orgName",
				title: "机构名称"
			},{
				field: "contactName",
				title: "联系人"
			},{
				field: "contactPhone",
				title: "联系方式"
			},{
				field: "address",
				title: "机构地址"
			},{
				field: "createTime",
				title: "创建时间",
				width: 175,
				templet: function(data){
			        return util.toDateString(data.createTime,_date_format);
			      }
			},{
				title: "操作",
				width: 150,
				align: "center",
				fixed: "right",
				toolbar: "#sys_org_toolbar"
			}]
		],
		text: {
			none: '暂无数据!'
		},
		request: {
			limitName: 'pageSize'
		  }
	}), table.on("tool(tbl_sys_org)", function(e) {
		e.data;
		if ("del" === e.event) layer.confirm("确定删除此系统机构？", function(_$) {
			$.ajax({ url: _contextPath + "admin/sys/org/org_del?orgId=" +e.data.orgId, success: function(result){
				if(result.code == 0){
					layer.msg('操作成功!', {icon: 6});
					table.reload("tbl_sys_org");
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
				title: "编辑资源",
				content: _contextPath + "admin/sys/org/org_edit?orgId=" +e.data.orgId,
				area: ["600px", "600px"],
				btn: ["确定", "取消"],
				yes: function(index, _dom) {
					var iframe = window["layui-layer-iframe" + index];
					var _submit = _dom.find("iframe").contents().find("#add-sysOrg-submit");
					iframe.layui.form.on("submit(add-sysOrg-submit)", function(_dom) {   
						$.ajax({ url: _contextPath + "admin/sys/org/org_edit",type: 'POST', data: _dom.field, success: function(result){
							if(result.code == 0){
								layer.msg('操作成功!', {icon: 6});
								table.reload("tbl_sys_org");
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
	});
  
    $('#add_org').on('click', function(){
    	layer.open({
    		type: 2
    		,title: '添加机构'
    		,content: _contextPath + 'admin/sys/org/org_add'
            ,area: ['600px', '600px']
            ,btn: ['确定', '取消']
            ,yes: function(index, _dom){
              var iframe = window['layui-layer-iframe'+ index];
              var _submit = _dom.find('iframe').contents().find("#add-sysOrg-submit");
              iframe.layui.form.on("submit(add-sysOrg-submit)", function(_dom) {
            	  var _is_auth_empty = true;
            	  for(var _key in _dom.field){
            		  if(_key.indexOf('authids') != -1){
            			  _is_auth_empty = false;
            			  break;
            		  }
                  }
            	  if(_is_auth_empty){
            		  layer.msg('请选择权限!',{icon: 0});
            		  return;
            	  }
            	  $.ajax({ url: _contextPath + "admin/sys/org/org_add",type: 'POST', data: _dom.field, success: function(result){
            		  if(result.code == 0){
            			  layer.msg('操作成功!', {icon: 6});
						  table.reload("tbl_sys_org");
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