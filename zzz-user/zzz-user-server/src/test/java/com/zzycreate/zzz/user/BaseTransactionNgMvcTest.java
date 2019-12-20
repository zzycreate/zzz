package com.zzycreate.zzz.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;

/**
 * 测试Controller使用
 *
 * @author zhenyao.zhao
 * @date 2019/11/21
 */
@SpringBootTest
public class BaseTransactionNgMvcTest extends AbstractTransactionalTestNGSpringContextTests {

    //注入web环境的ApplicationContext容器
    @Autowired
    protected WebApplicationContext wac;

    protected MockMvc mockMvc;

    //BeforeClass会在testcase执行之前执行
    @BeforeClass
    public void setUp() {
        //MockMvcBuilders.webAppContextSetup(wac).build()创建一个MockMvc进行测试
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
}
