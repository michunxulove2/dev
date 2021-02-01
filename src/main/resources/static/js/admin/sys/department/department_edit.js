function callback(){
	var $ = layui.$;
	var form = layui.form;

	form.verify({
		name: function (value,item) {
			if (!value) {
				return '请输入部门名称';
			}
		}
	});

}