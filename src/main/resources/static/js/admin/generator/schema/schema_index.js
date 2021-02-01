 function callback(){
    var $ = layui.$;
    var form = layui.form;
    var table = layui.table;
    
    table.render({
		elem: "#tbl_schema_list",
		url: _contextPath + "admin/generator/schema/schema_data_list",
		height: 'full-180',
		cols: [
			[{
				type: "radio"
			},{
				field: "tblName",
				title: "表名"
			},{
				field: "tblComment",
				title: "备注"
			},{
				field: "tblEngine",
				title: "引擎"
			}]
		],
		text: {
			none: '暂无数据!'
		}
	});
    
    table.on('radio(tbl_schema_list)', function(event){
    	if($('#full_gen_code').hasClass('layui-btn-disabled')){
    		$('#full_gen_code').removeAttr('disabled').removeClass('layui-btn-disabled');
    	}
	});
    
    $('#full_gen_code').on('click', function(){
    	var checkStatus = table.checkStatus('tbl_schema_list');
    	var tableName = 'tableName=' + checkStatus.data[0].tblName;
    	layer.open({
    		type: 2
    		,title: '代码生成'
    		,content: _contextPath + 'admin/generator/schema/full_gen_code?' + tableName
            ,area: ['900px', '600px']
            ,btn: ['确定', '取消']
            ,yes: function(index, _dom){
            	var iframe = window['layui-layer-iframe'+ index];
                var _submit = _dom.find('iframe').contents().find("#gen-code-submit");
                iframe.layui.form.on("submit(gen-code-submit)", function(_dom) {
	              	  $.ajax({ url: _contextPath + "admin/generator/schema/full_gen_code",type: 'POST', data: _dom.field, success: function(result){
	              		  if(result.code == 0){
	              			  layer.msg('操作成功!', {icon: 6});
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