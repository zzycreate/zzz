package com.zzycreate.zzz.application.kdn;

import com.zzycreate.zzz.application.kdn.model.KdnRequest;
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

    @PostMapping(
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, "charset=utf-8"}
    )
    KdnTrackQueryResponse query(@RequestBody KdnRequest kdnRequest);

}
