<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%--<%@ include file="/WEB-INF/views/include/taglib.jsp"%>--%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>三方支付</title>
</head>
<body>
<H1 align="center">支付测试</H1>
<h5 align="center"><a style="color: red">为防止订单重复,在代码中模拟生成了唯一订单,此处可直接提交</a></h5>
<hr>
<div class="form-container" align="center">

    <form id="form"  method="post">
        *商户订单 :
        <input type="text" name="outTradeNo" value="order10001100001"><br>
        *订单名称 :
        <input type="text" name="subject" value="iPhone X🅂 Max"><br>
        *付款金额 :
        <input type="text" name="totalAmount" value="0.01"><br>
        *商品描述 :
        <input type="text" name="body" value="Bigger than bigger"><br>
        <input type="button" value="支付宝支付" onclick="submitForm('/pay?type=1')">
        <input type="button" value="连连支付  " onclick="submitForm('/pay?type=2')">
    </form>
</div>
</body>

<script language="javascript">
    function submitForm(action) {
        document.getElementById("form").action = action
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