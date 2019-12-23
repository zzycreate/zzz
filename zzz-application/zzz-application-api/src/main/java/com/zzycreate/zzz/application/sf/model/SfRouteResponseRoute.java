package com.zzycreate.zzz.application.sf.model;

import lombok.ToString;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.Date;

/**
 * @author zhenyao.zhao
 * @date 2019/12/23
 */
@ToString
public class SfRouteResponseRoute {

    /**
     * 路由节点发生的时间,格式:YYYY-MM-DD HH24:MM:SS,示例:2012-7-30 09:30:00。
     */
    @XmlAttribute(name = "accept_time")
    public Date acceptTime;
    /**
     * 路由节点发生的地点
     */
    @XmlAttribute(name = "accept_address")
    public String acceptAddress;
    /**
     * 路由节点具体描述
     */
    @XmlAttribute(name = "remark")
    public String remark;
    /**
     * 路由节点操作码
     */
    @XmlAttribute(name = "opcode")
    public String opcode;

}
