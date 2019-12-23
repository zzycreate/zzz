package com.zzycreate.zzz.application.kdn;

import com.zzycreate.zzz.application.BaseNgTest;
import com.zzycreate.zzz.application.kdn.model.KdnRequest;
import com.zzycreate.zzz.application.kdn.model.factory.KdnRequestFactory;
import com.zzycreate.zzz.application.kdn.model.track.KdnTrackQuery;
import com.zzycreate.zzz.application.kdn.model.track.KdnTrackQueryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.testng.annotations.Test;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static org.testng.Assert.assertNotNull;

/**
 * @author zzycreate
 * @date 2019/12/22
 */
@Slf4j
public class KdnTrackQueryApiTest extends BaseNgTest {

    @Value("${kdn.businessid}")
    private String businessid;
    @Value("${kdn.appkey}")
    private String appkey;

    @Resource
    private KdnTrackQueryApi kdnTrackQueryApi;

    @Test
    public void testQuery() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        KdnTrackQuery ktq = KdnTrackQuery.builder().shipperCode("SF").logisticCode("118650888018").build();
        KdnRequest request = KdnRequestFactory.buildTrackQueryRequest(ktq, businessid, appkey);
        KdnTrackQueryResponse response = this.kdnTrackQueryApi.query(request);
        log.info("{}", response);
        assertNotNull(response);
    }
}