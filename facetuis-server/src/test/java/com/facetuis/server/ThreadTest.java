package com.facetuis.server;

import com.facetuis.server.service.pinduoduo.task.OrderTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = {"classpath:facetuis.properties"})
public class ThreadTest {
    @Autowired
    private OrderTask orderTask;

    @Test
    public void test(){

    }
}
