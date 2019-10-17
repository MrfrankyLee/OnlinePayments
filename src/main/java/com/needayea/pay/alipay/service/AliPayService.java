package com.needayea.pay.alipay.service;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.needayea.pay.alipay.bean.AlipayBean;
import com.needayea.pay.alipay.config.AlipayConfig;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * 支付宝支付
 * @author lixiaole
 */
@Service
public class AliPayService {


    /**
     * 支付宝PC支付
     */
    public String pcAlipay(String outTradeNo, String subject, String totalAmount, String body, String notifyUrl, HttpServletRequest request, HttpServletResponse response){
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
        return form;
    }

    /**
     * 支付宝H5支付
     */
    public String h5Alipay(String outTradeNo, String subject, BigDecimal totalAmount, String body, String notifyUrl, HttpServletRequest request, HttpServletResponse response){

        // 1、获得初始化的AlipayClient
        AlipayClient client = new DefaultAlipayClient(AlipayConfig.GATEWAY_URL, AlipayConfig.APP_ID, AlipayConfig.PRIVARY_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGN_TYPE);
        // 2、设置请求参数
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
        // 封装请求支付信息
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(outTradeNo);
        model.setSubject(subject);
        model.setTotalAmount(totalAmount.toString());
        model.setBody(body);
        model.setProductCode(AlipayConfig.PRODUCT_CODE);
        alipayRequest.setBizModel(model);
        alipayRequest.setNotifyUrl(AlipayConfig.NOTIFY_URL);
        alipayRequest.setReturnUrl(AlipayConfig.RETURN_URL);
        String form = "";
        try {
            // 调用SDK生成表单
            response.reset();
            form = client.pageExecute(alipayRequest).getBody();
            response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
            response.getOutputStream().write(form.getBytes());
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (AlipayApiException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            e.printStackTrace();
        }
        return form;
    }

}
