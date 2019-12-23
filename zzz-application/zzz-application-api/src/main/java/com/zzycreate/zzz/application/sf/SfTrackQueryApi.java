package com.zzycreate.zzz.application.sf;

import com.zzycreate.zzz.application.sf.model.SfBody;
import com.zzycreate.zzz.application.sf.model.factory.SfRequestFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author zhenyao.zhao
 * @date 2019/12/23
 */
@FeignClient(value = "sf", contextId = "sfTrackQuery", url = "${zf.feign.url.sf.trackQuery}")
public interface SfTrackQueryApi {

    /**
     * 查询轨迹
     *
     * @param body 请求内容，请使用{@link SfRequestFactory#buildTrackRequestBody(String, String, String, String)}
     * @return 响应结果
     */
    @PostMapping(
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE
    )
    String query(SfBody body);

}
