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

}
