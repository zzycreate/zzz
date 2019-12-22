package com.zzycreate.zzz.application.kdn.model.track;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zzycreate
 * @date 2019/12/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KdnShipper {

    /**
     * 快递公司编码	O
     */
    @JsonProperty("ShipperCode")
    private String shipperCode;
    /**
     * 快递公司名称	O
     */
    @JsonProperty("ShipperName")
    private String shipperName;

}
