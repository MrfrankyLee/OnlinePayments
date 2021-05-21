package com.needayea.pay.alipay.service;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.needayea.pay.Interfaces.request.PayRequest;
import com.needayea.pay.alipay.bean.AlipayBean;
import com.needayea.pay.alipay.config.AlipayConfig;
import com.needayea.pay.service.IPayment;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 支付宝支付
 *
 * @author lixiaole
 */
@Service("AliPcPay")
public class AliPayPcService implements IPayment {

    @Override
    public String pay(PayRequest payRequest) {
        AlipayBean alipayBean = new AlipayBean();
        alipayBean.setOut_trade_no(payRequest.getOrderId());
        alipayBean.setSubject(payRequest.getGoodsName());
        alipayBean.setTotal_amount(String.valueOf(payRequest.getTotalAmount().doubleValue()));
        alipayBean.setBody(payRequest.getInfo());
        // 1、获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.GATEWAY_URL, AlipayConfig.APP_ID, AlipayConfig.PRIVARY_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.PUBLIC_KEY, AlipayConfig.SIGN_TYPE);
        // 2、设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        // 页面跳转同步通知页面路径
        alipayRequest.setReturnUrl(AlipayConfig.RETURN_URL);
        // 服务器异步通知页面路径
        alipayRequest.setNotifyUrl(AlipayConfig.NOTIFY_URL);
        // 封装参数
        alipayRequest.setBizContent(JSON.toJSONString(alipayBean));
        String form = "";
        try {
            // 调用SDK生成表单
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
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
}
