package com.zzycreate.zzz.user.test;

import com.zzycreate.zf.feign.IdApi;
import com.zzycreate.zzz.user.BaseNgTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.Test;

import javax.annotation.Resource;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * @author zzycreate
 * @date 2019/12/19
 */
@Slf4j
public class IdApiTest extends BaseNgTest {

    @Resource
    private IdApi idApi;

    @Test
    public void getId() {
        Long id = this.idApi.getId("zzz-user_test-id");
        log.info("{}", id);
        assertNotNull(id);

        String strId = this.idApi.getStrId("zzz-user_test-id");
        log.info("{}", strId);
        assertNotNull(strId);
        assertTrue(StringUtils.isNotBlank(strId));
    }
}
