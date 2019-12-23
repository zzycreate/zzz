package com.zzycreate.zzz.application.kdn.model.track;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author zzycreate
 * @date 2019/12/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KdnTrackQueryResponse implements Serializable {

    private static final long serialVersionUID = 700183753928241190L;
    /**
     * 快递公司编码 	R
     */
    @JsonProperty("ShipperCode")
    private String shipperCode;
    /**
     * 物流单号 R
     */
    @JsonProperty("LogisticCode")
    private String logisticCode;
    /**
     * 订单编号 O
     */
    @JsonProperty("OrderCode")
    private String orderCode;

    /**
     * 用户ID
     */
    @JsonProperty("EBusinessID")
    private String eBusinessId;
    /**
     * 成功与否
     */
    @JsonProperty("Success")
    private Boolean success;
    /**
     * 失败原因
     */
    @JsonProperty("Reason")
    private String reason;
    /**
     * 物流状态：2-在途中,3-签收,4-问题件
     */
    @JsonProperty("State")
    private String state;
    /**
     * 物流轨迹
     */
    @JsonProperty("Traces")
    private List<KdnTrace> traces;

    //单号识别对象参数
    /**
     * 失败原因
     */
    @JsonProperty("Code")
    private Integer code;
    /**
     * 快递公司对象
     */
    @JsonProperty("Shippers")
    private List<KdnShipper> shippers;
    
}
