package com.zzycreate.zzz.application.kdn;

import com.zzycreate.zzz.application.kdn.model.KdnRequest;
import com.zzycreate.zzz.application.kdn.model.KdnRequestFactory;
import com.zzycreate.zzz.application.kdn.model.track.KdnTrackQuery;
import com.zzycreate.zzz.application.kdn.model.track.KdnTrackQueryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author zzycreate
 * @date 2019/12/22
 */
@FeignClient(value = "kdn", contextId = "trackQuery", url = "${zf.feign.url.kdn.trackQuery}")
public interface KdnTrackQueryApi {

    /**
     * 快递鸟即时查询API
     *
     * @param kdnRequest 请求， 请使用{@link KdnRequestFactory#buildTrackQueryRequest(KdnTrackQuery, String, String)}构建
     * @return 查询结果
     */
    @PostMapping(
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, "charset=utf-8"}
    )
    KdnTrackQueryResponse query(@RequestBody KdnRequest kdnRequest);

}
