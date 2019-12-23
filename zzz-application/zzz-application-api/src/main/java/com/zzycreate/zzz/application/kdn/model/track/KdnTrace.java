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
public class KdnTrace implements Serializable {

    private static final long serialVersionUID = 3374370723786469524L;
    /**
     * 时间
     */
    @JsonProperty("AcceptTime")
    private String acceptTime;
    /**
     * 描述
     */
    @JsonProperty("AcceptStation")
    private String acceptStation;
    /**
     * 备注
     */
    @JsonProperty("Remark")
    private String remark;
    
}
