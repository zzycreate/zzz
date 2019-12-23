package com.zzycreate.zzz.application.sf;

import com.zzycreate.zzz.application.BaseNgTest;
import com.zzycreate.zzz.application.sf.model.SfBody;
import com.zzycreate.zzz.application.sf.model.SfResponse;
import com.zzycreate.zzz.application.sf.model.factory.SfRequestFactory;
import com.zzycreate.zzz.application.sf.utils.JaxbXmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.testng.annotations.Test;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import static org.testng.Assert.assertNotNull;

/**
 * @author zhenyao.zhao
 * @date 2019/12/23
 */
@Slf4j
public class SfTrackQueryApiTest extends BaseNgTest {

    @Value("${sf.code}")
    private String sfCode;
    @Value("${sf.checkword}")
    private String checkword;

    @Resource
    private SfTrackQueryApi sfTrackQueryApi;

    @Test
    public void testQuery() throws JAXBException, NoSuchAlgorithmException, XMLStreamException {
        SfBody map = SfRequestFactory.buildTrackRequestBody("755123456789", "1234", sfCode, checkword);
        String result = this.sfTrackQueryApi.query(map);
        SfResponse sfResponse = JaxbXmlUtil.toBean(result, SfResponse.class);
        log.info("{}", sfResponse);
        assertNotNull(sfResponse);
    }
}
