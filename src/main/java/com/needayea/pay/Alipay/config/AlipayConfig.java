package com.needayea.pay.Alipay.config;

import org.springframework.stereotype.Component;

/**
 *  支付宝第三方支付参数配置
 */
@Component
public class AlipayConfig {

    //商户APPID
    public static  String APP_ID = "2016092800614900";

    // 私钥
    public static  String PRIVARY_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCPPihSCzEBK0K20AlEZidPQrPU31FGIJLZky+dg6dtX+tU+imB+0o6hzhTRlY8OJ/ZYInPkCrCGJ0ewkikQbsSS9afFZ41n/1ntIWjyDKrcv3eXYqzpPqGBE5v5QpBejYm1zUim0h0E+W/xIoh2/0WROPJkGZE57aF60k7MzgXiHGk5UauC44JuOie+LSkcCb5o2BgnHf6l+dFxtJ69n8NUYP1dsPySm9HgZyCxFbkXxHpFb1Kzzgt3WrJ8xtXMEJ1dQKHLKENjeOX1a6UeLiS4NpZeQgn0/kQYU93Srr6kyGUYpSdVZ96SfWiamsjyNma3JTsw5UjLNgd6XH3L1XTAgMBAAECggEAIaIanYj7LKcXtw0J4AGMYVPsHaOB1dF4KZYhq/5ppYjMHNvQOzDhsPRnCUaOoP06qzO7p/zVzVUyLzK3E7pLTnR4JEpTJ4w1V9BPQLGVFCSf70JPevbVy/ne4O8KhW1Iob4OP93uGTNC0tA3DtQ0jLIIOh+po6w0cyVXwIq6NOv+sWO93IvE9OhmLMOqIyfe99LPA8nexM7s2MNkc0i17UheqqIhel6PjQ1XwZOlMvU98LmbUht1u29ZxsaYJq1Tpa56koGrSU+1Gt17XUtjghSdctTKpjZ0d0XqWmNuNA5YF8wEfXYsFrWNbDxppWFRw2wwiRx850Ttmvs97wjA4QKBgQDDTP9XN+PRaCfQ1cJ5kFoXouZ0sfM7EbELgC1feb3wd8UrjE/lm1GpJPqWmx271sgOvinJ+2URd3THwa4/fEh6xJm6/dui38kNHf76LLPs9jPZICHSbpJMCaxvQFGzIAktBzARUJsrY3ZXMDp+4+U6/9tj68qi+TlRdV/hFXkgnQKBgQC7wzICWwtMBm1OiJDROXOFVC4AIpGBVyOnRdQONefROr39MZK8l8lLkJQeemSt+ab344aq6eMAEWy+FLTwZX6qj3tiI11oM9ddHvYTt/oErvlGCoWiRpSdBszOG/4DASBHXWp5BvHLHadMKo3Je6oBabqFXJaf/AEnO9rNd3XtLwKBgGSHD7APXr79g/Ek9rOoMBeWjFVo+7BeDOnEkpZgjfpnTCB8WuvvJPuRw0MemRGFHvknvU7wjjeNa5kfAtg+2vgGlrL/1LxjwJN16zdPJ1ZD7LBIr+AndjqZiB7D1soDJXX+Cyn+ecC9s75xXsrN98KEzKMETgvRiUV6y12KtgypAoGBALIwDV6k7nSoyMU131Ffl10ZxUoFhFbdzo0pPJR73dL3T2RgzmPCHJtkytydxLxCZ41q1NuQeQgQgUVmu0xE1c+huTMqYrPINwM9pcSS3Wficdhhle6p0tGuqWCUuhw5zltLsQbIa2EadDFZVRO4E1+h6gw0ERIlAycrq3tAep6rAoGBAI2aIItBak+mWKUAiyYMOLBjgcb9eTBQ14s/FNiqyS11ZtgyzD7tRaEBlhwQah9GQ9oDEKD2/P5vryEu5ugdgDlCZ6aeigQ/Gz/XPMHEslvczX7I5i2TRpqAGN/fmuSFaT7r8Ygd4xXsvBb+TXYcu4Omdc8POIY9g09sVINwns72";

    // 应用公钥
    public static  String PUBLIC_KEY ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjz4oUgsxAStCttAJRGYnT0Kz1N9RRiCS2ZMvnYOnbV/rVPopgftKOoc4U0ZWPDif2WCJz5AqwhidHsJIpEG7EkvWnxWeNZ/9Z7SFo8gyq3L93l2Ks6T6hgROb+UKQXo2Jtc1IptIdBPlv8SKIdv9FkTjyZBmROe2hetJOzM4F4hxpOVGrguOCbjonvi0pHAm+aNgYJx3+pfnRcbSevZ/DVGD9XbD8kpvR4GcgsRW5F8R6RW9Ss84Ld1qyfMbVzBCdXUChyyhDY3jl9WulHi4kuDaWXkIJ9P5EGFPd0q6+pMhlGKUnVWfekn1omprI8jZmtyU7MOVIyzYHelx9y9V0wIDAQAB";

    // 支付宝公钥
    public static  String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvS/JAFVwYes0nnjfucalsUacjCh7X0JMjdFfjwoee6smmymOoT78oRpFzHS46VGEJvbV4xwnk113/fk9xQHP6j4Re7ImmRKfnTktUyb5Ev1z7lNnzAT5pHNNmk33voBq6FQaAt2rjT6g7OkLz0PIGgG5OsY2epnHPSzGSk79t6x7mVfgVE45cN7ewUhz7YVNnH2o1QG9fIxNoXSILpU3Gf+YRSZ3fa2ev7Y0ApthyxdAirMOCm4Xlkv+KImYeI8HHv63TeWssNArH1XEFydCTLY7XZbVMo4jgWZXbHReDA6G+DYu0rtcKaLp/g0MfuP5tFOa161DuOu3w8BVeit2dQIDAQAB";

    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static  String NOTIFY_URL = "http://petch.free.idcfengye.com/alipay/orderNotify";
    //跳转页面，买家支付成功后跳转的页面
    public static  String RETURN_URL = "http://petch.free.idcfengye.com/returnUrl";
    // RSA2
    public static  String SIGN_TYPE = "RSA2";
    // 编码
    public static  String CHARSET = "UTF-8";
    // 返回格式
    public static  String FORMAT = "json";
    // 日志记录目录
    public static  String LOG_PATH = "/log";
    //网关
    public static  String GATEWAY_URL = "https://openapi.alipaydev.com/gateway.do";
}
