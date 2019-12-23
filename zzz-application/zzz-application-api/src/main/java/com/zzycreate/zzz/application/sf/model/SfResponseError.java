package com.zzycreate.zzz.application.sf.model;

import lombok.ToString;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 * @author zhenyao.zhao
 * @date 2019/12/23
 */
@ToString
public class SfResponseError {

    @XmlAttribute(name = "code")
    public String code;

    @XmlValue
    public String value;

}
