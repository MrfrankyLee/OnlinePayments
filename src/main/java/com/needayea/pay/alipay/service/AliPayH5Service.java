package com.needayea.pay.alipay.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.needayea.pay.Interfaces.request.PayRequest;
import com.needayea.pay.alipay.config.AlipayConfig;
import com.needayea.pay.service.IPayment;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 支付宝H5支付
 * @author lixiaole
 */
@Service("AliH5Pay")
public class AliPayH5Service implements IPayment {

    @Override
    public String pay(PayRequest payRequest) {
        // 1、获得初始化的AlipayClient
        AlipayClient client = new DefaultAlipayClient(AlipayConfig.GATEWAY_URL, AlipayConfig.APP_ID, AlipayConfig.PRIVARY_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGN_TYPE);
        // 2、设置请求参数
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
        // 封装请求支付信息
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(payRequest.getOrderId());
        model.setSubject(payRequest.getOrderId());
        model.setTotalAmount(payRequest.getTotalAmount().toString());
        model.setBody(payRequest.getInfo());
        model.setProductCode(AlipayConfig.PRODUCT_CODE);
        alipayRequest.setBizModel(model);
        alipayRequest.setNotifyUrl(AlipayConfig.NOTIFY_URL);
        alipayRequest.setReturnUrl(AlipayConfig.RETURN_URL);
        String form = "";
        try {
            // 调用SDK生成表单
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
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
