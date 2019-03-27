# OnlinePayments
JavaWEB支付宝 连连第三方支付对接
@[TOC](JavaWEB对接支付宝三方支付)
# 关于支付宝三方支付对接的详细流程

因为公司的业务发展,需要第三方在线支付的支撑,在产品同事及相关领导各方面综合考量之后，最终确定接入[蚂蚁金服(支付宝)](https://www.alipay.com/)与[连连支付](https://www.lianlianpay.com/)两家三方支付公司！本文作为第一篇,将基于java开发语言详细讲述关于支付宝的对接流程。接入前建议仔细阅读[官方文档](https://docs.open.alipay.com/270)，本文以下内容假设你以完成阅读,并对相关名词了解
## 准备工作

 完成支付宝企业账号的注册与认证,在此不再赘述
 
## 完善开放平台信息
  1. **[登陆蚂蚁金服](https://openhome.alipay.com/platform/manageHome.htm)**
  2. **选择入驻身份**-- 自研开发者
![选择入驻身份为自研开发者](https://img-blog.csdnimg.cn/20190327154358235.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlbGU1MjA4MA==,size_16,color_FFFFFF,t_70#pic_center))
 3. **选择[沙箱](https://baike.baidu.com/item/%E6%B2%99%E7%AE%B1/393318)环境**
       之所以选择沙箱环境,是方便在本地进行代码的编写调试![研发服务-沙箱环境](https://img-blog.csdnimg.cn/20190327155614323.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlbGU1MjA4MA==,size_16,color_FFFFFF,t_70#pic_center)
4. **配置沙箱环境公私钥**
 		配置完成以后如下图所示,具体的配置过程官方写的很详细,不再赘述
 		![在这里插入图片描述](https://img-blog.csdnimg.cn/20190327160359987.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlbGU1MjA4MA==,size_16,color_FFFFFF,t_70#pic_center)
   到此前面的准备工作与本地环境的配置基本完成，可以进行代码的编写的了

## 代码编写
   以 springboot+maven 结构为例
1. **引入相关jar包**
  ```pom.xml
		<!--支付宝支付jar包 -->
        <dependency>
            <groupId>com.alipay.sdk</groupId>
            <artifactId>alipay-sdk-java</artifactId>
            <version>3.4.49.ALL</version>
        </dependency>
        <!--fastjson 引入 -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.29</version>
        </dependency>
```
2. **支付所需配置信息**
```java
public class AlipayConfig {

    //商户APPID(沙箱环境在沙箱页面查看,正式环境需要申请应用,后面详细说明)
    public static  String APP_ID = "2016092800614900";

    // 应用私钥(即前面所生成的私钥)
    public static  String PRIVARY_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCPPihSCzEBK0K20AlEZidPQrPU31FGIJLZky+dg6dtX+tU+imB+0o6hzhTRlY8OJ/ZYInPkCrCGJ0ewkikQbsSS9afFZ41n/1ntIWjyDKrcv3eXYqzpPqGBE5v5QpBejYm1zUim0h0E+W/xIoh2/0WROPJkGZE57aF60k7MzgXiHGk5UauC44JuOie+LSkcCb5o2BgnHf6l+dFxtJ69n8NUYP1dsPySm9HgZyCxFbkXxHpFb1Kzzgt3WrJ8xtXMEJ1dQKHLKENjeOX1a6UeLiS4NpZeQgn0/kQYU93Srr6kyGUYpSdVZ96SfWiamsjyNma3JTsw5UjLNgd6XH3L1XTAgMBAAECggEAIaIanYj7LKcXtw0J4AGMYVPsHaOB1dF4KZYhq/5ppYjMHNvQOzDhsPRnCUaOoP06qzO7p/zVzVUyLzK3E7pLTnR4JEpTJ4w1V9BPQLGVFCSf70JPevbVy/ne4O8KhW1Iob4OP93uGTNC0tA3DtQ0jLIIOh+po6w0cyVXwIq6NOv+sWO93IvE9OhmLMOqIyfe99LPA8nexM7s2MNkc0i17UheqqIhel6PjQ1XwZOlMvU98LmbUht1u29ZxsaYJq1Tpa56koGrSU+1Gt17XUtjghSdctTKpjZ0d0XqWmNuNA5YF8wEfXYsFrWNbDxppWFRw2wwiRx850Ttmvs97wjA4QKBgQDDTP9XN+PRaCfQ1cJ5kFoXouZ0sfM7EbELgC1feb3wd8UrjE/lm1GpJPqWmx271sgOvinJ+2URd3THwa4/fEh6xJm6/dui38kNHf76LLPs9jPZICHSbpJMCaxvQFGzIAktBzARUJsrY3ZXMDp+4+U6/9tj68qi+TlRdV/hFXkgnQKBgQC7wzICWwtMBm1OiJDROXOFVC4AIpGBVyOnRdQONefROr39MZK8l8lLkJQeemSt+ab344aq6eMAEWy+FLTwZX6qj3tiI11oM9ddHvYTt/oErvlGCoWiRpSdBszOG/4DASBHXWp5BvHLHadMKo3Je6oBabqFXJaf/AEnO9rNd3XtLwKBgGSHD7APXr79g/Ek9rOoMBeWjFVo+7BeDOnEkpZgjfpnTCB8WuvvJPuRw0MemRGFHvknvU7wjjeNa5kfAtg+2vgGlrL/1LxjwJN16zdPJ1ZD7LBIr+AndjqZiB7D1soDJXX+Cyn+ecC9s75xXsrN98KEzKMETgvRiUV6y12KtgypAoGBALIwDV6k7nSoyMU131Ffl10ZxUoFhFbdzo0pPJR73dL3T2RgzmPCHJtkytydxLxCZ41q1NuQeQgQgUVmu0xE1c+huTMqYrPINwM9pcSS3Wficdhhle6p0tGuqWCUuhw5zltLsQbIa2EadDFZVRO4E1+h6gw0ERIlAycrq3tAep6rAoGBAI2aIItBak+mWKUAiyYMOLBjgcb9eTBQ14s/FNiqyS11ZtgyzD7tRaEBlhwQah9GQ9oDEKD2/P5vryEu5ugdgDlCZ6aeigQ/Gz/XPMHEslvczX7I5i2TRpqAGN/fmuSFaT7r8Ygd4xXsvBb+TXYcu4Omdc8POIY9g09sVINwns72";

    // 应用公钥(即前面所生成的公钥)
    public static  String PUBLIC_KEY ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjz4oUgsxAStCttAJRGYnT0Kz1N9RRiCS2ZMvnYOnbV/rVPopgftKOoc4U0ZWPDif2WCJz5AqwhidHsJIpEG7EkvWnxWeNZ/9Z7SFo8gyq3L93l2Ks6T6hgROb+UKQXo2Jtc1IptIdBPlv8SKIdv9FkTjyZBmROe2hetJOzM4F4hxpOVGrguOCbjonvi0pHAm+aNgYJx3+pfnRcbSevZ/DVGD9XbD8kpvR4GcgsRW5F8R6RW9Ss84Ld1qyfMbVzBCdXUChyyhDY3jl9WulHi4kuDaWXkIJ9P5EGFPd0q6+pMhlGKUnVWfekn1omprI8jZmtyU7MOVIyzYHelx9y9V0wIDAQAB";

    // 支付宝公钥
    public static  String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvS/JAFVwYes0nnjfucalsUacjCh7X0JMjdFfjwoee6smmymOoT78oRpFzHS46VGEJvbV4xwnk113/fk9xQHP6j4Re7ImmRKfnTktUyb5Ev1z7lNnzAT5pHNNmk33voBq6FQaAt2rjT6g7OkLz0PIGgG5OsY2epnHPSzGSk79t6x7mVfgVE45cN7ewUhz7YVNnH2o1QG9fIxNoXSILpU3Gf+YRSZ3fa2ev7Y0ApthyxdAirMOCm4Xlkv+KImYeI8HHv63TeWssNArH1XEFydCTLY7XZbVMo4jgWZXbHReDA6G+DYu0rtcKaLp/g0MfuP5tFOa161DuOu3w8BVeit2dQIDAQAB";

    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static  String NOTIFY_URL = "http://petch.free.idcfengye.com/alipay/orderNotify";
    //跳转页面，买家支付成功后跳转的页面
    public static  String RETURN_URL = "http://petch.free.idcfengye.com/returnUrl";
    // RSA2
    public static  String SIGN_TYPE = "RSA2";
    // 编码格式
    public static  String CHARSET = "UTF-8";
    // 返回格式
    public static  String FORMAT = "json";
    // 日志记录目录
    public static  String LOG_PATH = "/log";
    //支付网关 正式环境("https://openapi.alipay.com/gateway.do";)无dev
    public static  String GATEWAY_URL = "https://openapi.alipaydev.com/gateway.do";
}
```
<font color=red>*需要注意的是NOTIFY_URL 和 RETURN_URL 所对应的api接口地址是发起支付完成后回调我们的地址(即我们在支付时就需要将这两个本地接口传递到支付宝),因此无论在线上还是本地环境都必须外网可以正常访问*  </font> 
因为我们在这里使用的是本地环境通过沙箱进行模拟测试,因此需要进行内网穿透,使得我们的本地ip可以映射到外网，在这里推荐[ngrok](http://www.ngrok.cc/),当然还有很多内网穿透工具,自行百度即可
 例如 本篇日志提到的NOTIFY_URL 和 RETURN_URL 通过
 ```java
 http://petch.free.idcfengye.com 映射自己的127.0.0.1:8080 后面接接口路径
 ```

3. 编写支付代码(此次仅讨论支付宝支付 连连支付代码可忽略)

支付页面示例
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190327165436171.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlbGU1MjA4MA==,size_16,color_FFFFFF,t_70#pic_center)
支付页面代码(index.jsp)
```java
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
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
```
bean层
```java
@Component
public class AlipayBean {
    /**
     * 商户订单号，必填
     *
     */
    private String out_trade_no;
    /**
     * 订单名称，必填
     */
    private String subject;
    /**
     * 付款金额，必填
     * 根据支付宝接口协议，必须使用下划线
     */
    private String total_amount;
    /**
     * 商品描述，可空
     */
    private String body;
    /**
     * 超时时间参数
     */
    private String timeout_express= "10m";
    /**
     * 产品编号
     */
    private String product_code= "FAST_INSTANT_TRADE_PAY";

    public String getOut_trade_no() {
        return out_trade_no;
    }
    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getTotal_amount() {
        return total_amount;
    }
    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public String getTimeout_express() {
        return timeout_express;
    }
    public void setTimeout_express(String timeout_express) {
        this.timeout_express = timeout_express;
    }
    public String getProduct_code() {
        return product_code;
    }
    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

}
```
controller层
```java
 /**
     *
     * @param outTradeNo 订单号
     * @param subject  商品名称
     * @param totalAmount  付款金额
     * @param body   商品描述
     * @param type   支付方式 1 支付宝支付 2 连连支付
     * @return String
     */
    @RequestMapping(value = "/pay")
    public String aliPay(String outTradeNo, String subject, BigDecimal totalAmount, String body,String type,HttpServletRequest request ,HttpServletResponse response){
        // 为防止订单号重否 此处模拟生成唯一订单号
        outTradeNo = PayUtils.createUnilCode();
        //支付宝支付
        if(type.equalsIgnoreCase("1")){
            return aliPayService.alipay(outTradeNo,subject,totalAmount.toString(),body,AlipayConfig.NOTIFY_URL,request,response);
        }
        //连连支付
        if(type.equalsIgnoreCase("2")){
            String url = "";
            try {
                url = lianlianPayService.lianlianpay(outTradeNo,subject,totalAmount.toString(),body, PartnerConfig.NOTIFY_URL,request,response);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(!StringUtils.isEmpty(url)){
                request.setAttribute("gateway_url", url);
                return "gotoPlainPay";
            }
        }

        return "payFail";
    }
```
service层
```java
public class AliPayService {
    // 支付宝支付
    public String alipay(String outTradeNo, String subject, String totalAmount, String body, String notifyUrl, HttpServletRequest request, HttpServletResponse response){
        AlipayBean alipayBean = new AlipayBean();
        alipayBean.setOut_trade_no(outTradeNo);
        alipayBean.setSubject(subject);
        alipayBean.setTotal_amount(totalAmount);
        alipayBean.setBody(body);
        // 1、获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.GATEWAY_URL, AlipayConfig.APP_ID, AlipayConfig.PRIVARY_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.PUBLIC_KEY,  AlipayConfig.SIGN_TYPE);
        // 2、设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        // 页面跳转同步通知页面路径
        alipayRequest.setReturnUrl(AlipayConfig.RETURN_URL);
        // 服务器异步通知页面路径
        alipayRequest.setNotifyUrl(notifyUrl);
        // 封装参数
        alipayRequest.setBizContent(JSON.toJSONString(alipayBean));
        String form = "";
        try {
            // 调用SDK生成表单
            response.reset();
            // 发起支付且成功以后会返回一个支付宝收银台页面的地址,按照官方demo直接到页面即可
            form = alipayClient.pageExecute(alipayRequest).getBody();
            response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
            response.getOutputStream().write(form.getBytes());
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (AlipayApiException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 返回付款信息
        return form;
    }
```
发起支付成功后,跳转到收银台页面
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190327172228617.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlbGU1MjA4MA==,size_16,color_FFFFFF,t_70#pic_center)到这里说明前面的配置信息全部正确且成功可以进行支付操作了
支付宝在沙箱环境提供了虚拟的买家和商家账户可以用此完成支付
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190327172538925.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlbGU1MjA4MA==,size_16,color_FFFFFF,t_70#pic_center)
两个账户与真实账户一样,有兴趣也可以把它充满
支付成功以后会回调前面我们传入的两个接口 一个异步回调一个同步回调
异步回调地址(在此接口可以处理相关订单业务)
```java
   /**
     * 支付宝异步回调
     */
    @RequestMapping(value = "/alipay/orderNotify", method = RequestMethod.POST)
    public void orderNotify(HttpServletResponse response, HttpServletRequest request) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        // 商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
        // 支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
        // 交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
        // 付款金额
        BigDecimal payMoney = new BigDecimal(request.getParameter("total_amount"));
        // 到账金额
        BigDecimal receiptMoney = new BigDecimal(request.getParameter("receipt_amount"));
        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//https://docs.open.alipay.com/270/
        boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY,
                AlipayConfig.CHARSET, "RSA2");

        if (verify_result) {// 验证成功
            // ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
            if (trade_status.equals("TRADE_FINISHED")) {
                // 判断该笔订单是否在商户网站中已经做过处理
                // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                // 请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                // 如果有做过处理，不执行商户的业务程序
                // 注意：
                // 如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                // 如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
            } else if (trade_status.equals("TRADE_SUCCESS")) {
                // 支付交易成功  处理订单
                System.out.println("交易成功");
            }
        } else {// 验证失败
            System.out.println("验证失败");
        }

    }
```
同步回调地址（成功后如下图简单示例）
```java
@RequestMapping(value = "/returnUrl", method = RequestMethod.GET)
    public String returnUrl(HttpServletResponse response, HttpServletRequest request) throws Exception {
        return "paySuccess";
    }
```
paySuccess.jsp 页面
```javascript
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
<H1 align="center">支付成功</H1>
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
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190327170958897.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlbGU1MjA4MA==,size_16,color_FFFFFF,t_70#pic_center)
至此，沙箱环境的支付流程全部完成,可以进入正式环境配置了。
## 正式环境配置
1. 进入相关页面![在这里插入图片描述](https://img-blog.csdnimg.cn/20190327173643303.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlbGU1MjA4MA==,size_16,color_FFFFFF,t_70#pic_center)
2. 申请创建应用
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190327173828218.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlbGU1MjA4MA==,size_16,color_FFFFFF,t_70#pic_center)
3. 配置公私钥并替换AlipayConfig类中的需要替换的配置信息 
		![在这里插入图片描述](https://img-blog.csdnimg.cn/20190327175341902.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlbGU1MjA4MA==,size_16,color_FFFFFF,t_70#pic_center)
		
		配置 该应用的公私钥 且对之前的里面的信息进行替换。
4. 签约审核(审核通过后即才可以进行线上支付)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190327180124504.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlbGU1MjA4MA==,size_16,color_FFFFFF,t_70#pic_center)
至此所有的流程全部走完