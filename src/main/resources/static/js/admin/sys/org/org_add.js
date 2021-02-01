 function callback(){
	var $ = layui.$;
    var form = layui.form;
    var authtree = layui.authtree;
    
    AMapUI.loadUI(['misc/PositionPicker'], function(PositionPicker) {
        var map = new AMap.Map('org_map',{
            zoom:16
        })
        var positionPicker = new PositionPicker({
            mode:'dragMap',//设定为拖拽地图模式，可选'dragMap'、'dragMarker'，默认为'dragMap'
            map:map//依赖地图对象
        });
        //TODO:事件绑定、结果处理等
        positionPicker.on('success', function(positionResult) {
        	$('#address').val(positionResult.address);
        	$('#lng').val(positionResult.position.lng);
        	$('#lat').val(positionResult.position.lat);
        });
        positionPicker.start();
    });
    
    // 初始化
	$.ajax({
		url: _contextPath + "admin/sys/job/job_2_res",
		dataType: 'json',
		success: function(result){
			// 渲染时传入渲染目标ID，树形结构数据（具体结构看样例，checked表示默认选中），以及input表单的名字
			authtree.render('#select_res', result.data.nodeList, {inputname: 'authids[]', layfilter: 'lay-check-auth', openall: true});
		}
	});
    
 }