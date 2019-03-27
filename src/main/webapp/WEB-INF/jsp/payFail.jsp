<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="imagemode" content="force">
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<H1 align="center">支付失败</H1>
<hr>
<div align="center">
    <a style="color: green" href="/">返回首页</a>
</div>

</body>

<script language="javascript">
    function submitForm(action) {
       // document.getElementById("form").action = action
        document.getElementById("form").submit()
    }
</script>

<style>
    .form-container {
        padding-top:10px;
    }
    input {
        margin:10px;

    }
</style>
</html>