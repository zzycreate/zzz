package com.zzycreate.zzz.application.sf.model;

import lombok.ToString;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * @author zhenyao.zhao
 * @date 2019/12/23
 */
@ToString
public class SfRouteResponse {

    /**
     * 顺丰运单号
     */
    @XmlAttribute(name = "mailno")
    public String mailno;
    /**
     * 客户订单号,按客户订单号查询时为必填。按顺丰运单号查询时为空。
     */
    @XmlAttribute(name = "orderid")
    public String orderId;

    @XmlElement(name = "RouteResponse")
    public List<SfRouteResponseRoute> routes;
}
