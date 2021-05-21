package com.needayea.pay.service;

import com.needayea.pay.Interfaces.request.PayRequest;

/**
 * @author lixiaole
 * @date 2021/5/18
 */
public interface IPayment {

    String pay(PayRequest payRequest);
}
