<#macro head title>
<!DOCTYPE html>
<html style="height: 100%;">
<head>
  <meta charset="UTF-8">
  <title>${title}</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="${application.getContextPath()}/plugins/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="${application.getContextPath()}/plugins/layui/css/admin.css" media="all">
  <link rel="icon" href="${application.getContextPath()}/bxwl.ico" type="image/x-icon" />
  <link rel="shortcut icon" href="${application.getContextPath()}/bxwl.ico" type="image/x-icon" />
  <#nested />
</head>
</#macro>

<#macro scripts>
<script>
var _contextPath = '${application.getContextPath()}/';
var _date_format = 'yyyy-MM-dd HH:mm:ss';
</script>
<script src="${application.getContextPath()}/plugins/layui/layui.js"></script>
<script>
    layui.define(['jquery'],function(exports){
        var $ = layui.jquery;
        $(document).ajaxComplete(function(event,request, settings){
            if(request.getResponseHeader){
                var sessionStatus = request.getResponseHeader("sessionstatus");
                if (sessionStatus == 'timeout') {
                    window.location.href = _contextPath+"login";
                    return;
                }
            }
            layer.close(layer.load(1, {shade: [0.5,'#fff']}));
        });
      $(document).ajaxSend(function(event,request, settings){
        layer.load(1, {shade: [0.5,'#fff']});
      });
    });
</script>
<#nested />
</#macro>