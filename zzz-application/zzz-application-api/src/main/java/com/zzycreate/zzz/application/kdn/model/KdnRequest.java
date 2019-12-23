package com.zzycreate.zzz.application.kdn.model;

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
public class KdnRequest implements Serializable {

    private static final long serialVersionUID = 1904630499893698274L;
    /**
     * 请求内容需进行URL(utf-8)编码。请求内容JSON格式，须和DataType一致。
     */
    @JsonProperty("RequestData")
    private String RequestData;
    /**
     * 商户ID，请在我的服务页面查看。
     */
    @JsonProperty("EBusinessID")
    private String EBusinessID;
    /**
     * 请求指令类型
     */
    @JsonProperty("RequestType")
    private String RequestType;
    /**
     * 数据内容签名：把(请求内容(未编码)+AppKey)进行MD5加密，然后Base64编码，最后 进行URL(utf-8)编码。
     */
    @JsonProperty("DataSign")
    private String DataSign;
    /**
     * 请求、返回数据类型
     */
    @JsonProperty("DataType")
    private String DataType;

}
