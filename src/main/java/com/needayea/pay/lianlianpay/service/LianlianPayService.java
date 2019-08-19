package com.needayea.pay.lianlianpay.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.needayea.pay.lianlianpay.bean.PaymentInfo;
import com.needayea.pay.lianlianpay.config.PartnerConfig;
import com.needayea.pay.lianlianpay.conn.HttpRequestSimple;
import com.needayea.pay.lianlianpay.utils.LLPayUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class LianlianPayService {


    public String lianlianpay(String outTradeNo, String subject, String totalAmount, String body, String notifyUrl, HttpServletRequest request, HttpServletResponse response) throws Exception {
            // 构造支付请求对象
            SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmss");
            PaymentInfo paymentInfo = new PaymentInfo();
            paymentInfo.setApi_version(PartnerConfig.VERSION);
            paymentInfo.setSign_type(PartnerConfig.SIGN_TYPE);
            paymentInfo.setTime_stamp(LLPayUtil.getCurrentDateTimeStr());
            paymentInfo.setOid_partner(PartnerConfig.OID_PARTNER);
            //买家id
            paymentInfo.setUser_id("399746893");
            paymentInfo.setBusi_partner(PartnerConfig.BUSI_PARTNER);
            paymentInfo.setNo_order(outTradeNo);
            paymentInfo.setDt_order(sdf.format(new Date()));
            paymentInfo.setMoney_order(totalAmount);
            paymentInfo.setNotify_url(PartnerConfig.NOTIFY_URL);
            paymentInfo.setUrl_return(PartnerConfig.URL_RETURN);
            paymentInfo.setRisk_item(createRiskItem(paymentInfo));
            paymentInfo.setFlag_pay_product("2");
            paymentInfo.setFlag_chnl("2");//web
            paymentInfo.setName_goods(subject);
            paymentInfo.setInfo_order(body);
            paymentInfo.setValid_order("10080");// 单位分钟，可以为空，默认7天
            // 加签名x
            String sign = LLPayUtil.addSign(JSON.parseObject(JSON
                            .toJSONString(paymentInfo)), PartnerConfig.TRADER_PRI_KEY,
                    PartnerConfig.MD5_KEY);
            paymentInfo.setSign(sign);

            String resJSON =  HttpRequestSimple.getInstance().postSendHttp(PartnerConfig.CreateBill_URL, JSON.toJSONString(paymentInfo));
            System.out.println("创单请求响应报文[" + resJSON + "]");

            JSONObject payDataBean = JSON.parseObject(resJSON);
            if(!"0000".equals(payDataBean.getString("ret_code"))){
                System.out.println("创单失败");
                throw new Exception("创单失败");
            }
            if (!LLPayUtil.checkSign(resJSON, PartnerConfig.YT_PUB_KEY, "")) {
                System.out.println("签名验证失败");
                throw new Exception("创单失败/创单签名验证失败");
            }
            return payDataBean.getString("gateway_url");
    }

    /**
     * 根据连连支付风控部门要求的参数进行构造风控参数
     * @return
     */
    private String createRiskItem(PaymentInfo paymentInfo)
    {
        JSONObject riskItemObj = new JSONObject();
        //商品类目
        riskItemObj.put("frms_ware_category", "4010");
        //用户ID
        riskItemObj.put("user_info_mercht_userno","399746893");
        //用户手机号码
        riskItemObj.put("user_info_bind_phon","18089899872");
        // 用户注册时间
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmss");
        riskItemObj.put("user_info_dt_register", sdf.format(new Date()));
        // 商品名称
        riskItemObj.put("goods_name","iPhone");
        //是否含有虚拟物品。0 - 无。1 - 有。
        riskItemObj.put("virtual_goods_status","0");
        //商品数量
        riskItemObj.put("goods_count","10");
        //收货人姓名
        riskItemObj.put("delivery_full_name","张三");
        //收货人联系方式。
        riskItemObj.put("delivery_phone","18800888800");
        //物流方式。 1 - 邮局平邮。2 - 普通快递。3 - 特快专递。4 - 物流货运公司。5 - 物流配送公司。6 - 国际快递。7 - 航运快递。8 - 海运。9 - 自提。
        riskItemObj.put("logistics_mode","4");
        //发货时间
        riskItemObj.put("delivery_cycle", "other");
        //收货地址省级编码
        riskItemObj.put("frms_ware_category", "310000");
        //收货地址市级编码
        riskItemObj.put("frms_ware_category", "310101");
        return riskItemObj.toString();
    }
}
