 function callback(){
	var $ = layui.$;
    var form = layui.form;

    form.verify({
        dictName: function (value,item) {
            // if(!new RegExp("^[a-zA-Z0-9\\s·]+$").test(value)){
            //     return '不能有特殊字符及中文';
            // }
            // if (value.length > 20) {
            //     return '长度不符合规范,超过20位';
            // }
        },
        dictType: function (value,item) {
            // if (value.length > 20) {
            //     return '长度不符合规范,超过20位';
            // }
        }
    });
    
 }