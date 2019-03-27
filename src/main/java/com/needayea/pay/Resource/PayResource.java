package com.needayea.pay.Resource;

import com.alibaba.fastjson.JSON;
import com.alipay.api.internal.util.AlipaySignature;
import com.needayea.pay.AliPay.config.AlipayConfig;
import com.needayea.pay.AliPay.service.AliPayService;
import com.needayea.pay.LianLianPay.bean.PayDataBean;
import com.needayea.pay.LianLianPay.bean.RetBean;
import com.needayea.pay.LianLianPay.config.PartnerConfig;
import com.needayea.pay.LianLianPay.service.LianlianPayService;
import com.needayea.pay.LianLianPay.utils.LLPayUtil;
import com.needayea.pay.utils.PayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@RequestMapping
public class PayResource {

    @Autowired
    private AliPayService aliPayService;
    @Autowired
    private LianlianPayService lianlianPayService;

    /**
     * 提交订单跳转到第三方收银台
     * @param outTradeNo 订单号
     * @param subject  商品名称
     * @param totalAmount  付款金额
     * @param body   商品描述
     * @param type   支付方式 1 支付宝支付 2 连连支付
     * @return String
     */
    @RequestMapping(value = "/pay")
    public String pay(String outTradeNo, String subject, BigDecimal totalAmount, String body,String type,HttpServletRequest request ,HttpServletResponse response){
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

    /**
     * 支付宝异步回调
     */
    @RequestMapping(value = "/alipay/orderNotify", method = RequestMethod.POST)
    public void orderNotifyByAli(HttpServletResponse response, HttpServletRequest request) throws Exception {
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


    @RequestMapping(value = "/returnUrl", method = RequestMethod.GET)
    public String returnUrl(HttpServletResponse response, HttpServletRequest request) throws Exception {
        return "paySuccess";
    }

    /**
     *  连连支付异步回调地址
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/lianLianpay/orderNotify", method = RequestMethod.POST)
    protected void orderNotifyByLianLian(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        System.out.println("进入支付异步通知数据接收处理");
        RetBean retBean = new RetBean();
        String reqStr = LLPayUtil.readReqStr(req);
        if (LLPayUtil.isnull(reqStr))
        {
            retBean.setRet_code("9999");
            retBean.setRet_msg("交易失败");
            resp.getWriter().write(JSON.toJSONString(retBean));
            resp.getWriter().flush();
            return;
        }
        System.out.println("接收支付异步通知数据：【" + reqStr + "】");
        try
        {
            if (!LLPayUtil.checkSign(reqStr, PartnerConfig.YT_PUB_KEY,
                    ""))
            {
                retBean.setRet_code("9999");
                retBean.setRet_msg("交易失败");
                resp.getWriter().write(JSON.toJSONString(retBean));
                resp.getWriter().flush();
                System.out.println("支付异步通知验签失败");
                return;
            }
        } catch (Exception e)
        {
            System.out.println("异步通知报文解析异常：" + e);
            retBean.setRet_code("9999");
            retBean.setRet_msg("交易失败");
            resp.getWriter().write(JSON.toJSONString(retBean));
            resp.getWriter().flush();
            return;
        }
        retBean.setRet_code("0000");
        retBean.setRet_msg("交易成功");
        resp.getWriter().write(JSON.toJSONString(retBean));
        resp.getWriter().flush();
        System.out.println("支付异步通知数据接收处理成功");
        // 解析异步通知对象
        PayDataBean payDataBean = JSON.parseObject(reqStr, PayDataBean.class);
        if(payDataBean.getResult_pay().equalsIgnoreCase("SUCCESS")){
            System.out.println("用户支付成功");
           // 更新订单操作
        }
    }

}
