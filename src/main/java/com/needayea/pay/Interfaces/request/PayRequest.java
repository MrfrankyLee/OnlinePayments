package com.needayea.pay.Interfaces.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lixiaole
 * @date 2021/5/18
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayRequest implements Serializable {

    private String orderId;

    private String goodsName;

    private BigDecimal totalAmount;

    private String info;
}
