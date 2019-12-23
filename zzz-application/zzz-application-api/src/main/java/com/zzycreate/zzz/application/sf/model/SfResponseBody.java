package com.zzycreate.zzz.application.sf.model;

import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * @author zhenyao.zhao
 * @date 2019/12/23
 */
@ToString
public class SfResponseBody {

    @XmlElement(name = "RouteResponse")
    public List<SfRouteResponse> routeResponses;

}
