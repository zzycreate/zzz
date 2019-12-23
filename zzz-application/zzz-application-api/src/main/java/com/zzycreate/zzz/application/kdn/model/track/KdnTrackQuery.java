package com.zzycreate.zzz.application.kdn.model.track;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zzycreate
 * @date 2019/12/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KdnTrackQuery implements Serializable {

    private static final long serialVersionUID = -2336828770654335589L;
    /**
     * 快递公司编码
     */
    @JsonProperty("ShipperCode")
    private String shipperCode;
    /**
     * 物流单号
     */
    @JsonProperty("LogisticCode")
    private String logisticCode;
    /**
     * 订单编号
     */
    @JsonProperty("OrderCode")
    private String orderCode;
    /**
     * 非快递鸟渠道下单（电子面单）返回的顺丰单号，需ShipperCode快递公司编码+LogisticCode快递单号＋CustomerName手机号查询，
     * CustomerName传值收件人或寄件人手机号后四位数字。请求报文格式{'LogisticCode':'233823364856','ShipperCode':'SF','CustomerName':'1234'}；
     */
    @JsonProperty("CustomerName")
    private String customerName;

}
