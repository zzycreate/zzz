package com.zzycreate.zzz.application.sf.model;

import lombok.ToString;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author zhenyao.zhao
 * @date 2019/12/23
 */
@ToString
@XmlRootElement(name = "Response")
public class SfResponse {
    @XmlAttribute(name = "service")
    public String service;
    @XmlAttribute(name = "lang")
    public String lang;

    @XmlElement(name = "Head")
    public String head;
    @XmlElement(name = "Body")
    public SfResponseBody body;
    @XmlElement(name = "ERROR")
    public SfResponseError error;

}
