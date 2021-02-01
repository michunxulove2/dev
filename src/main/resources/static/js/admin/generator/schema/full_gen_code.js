 function callback(){
	 var $ = layui.$;
	 var form = layui.form;
	 var table = layui.table;
	 
	 $('#root_module').change(function(){
		updatePath($,form);
	 });
	 
	 $('#module_name').change(function(){
		updatePath($,form);
	 });
 
 }
 
 function updatePath($,form){
	 var _module_name = $('#module_name').val();
	 var _basePath = $('#root_module').val() + '.' + _module_name;
	 
	 form.val("form-full-gen-code", {
		"modelPath": _basePath + '.model',
		controllerPath: _basePath + '.controller',
		servicePath: _basePath + '.service',
		daoPath: _basePath + '.dao',
		mapperPath: _basePath + '.dao.mapper',
		ftlPath: _module_name,
		jsPath: _module_name
	});
 }