package com.facetuis.server;

import com.facetuis.server.service.inviting.InvitingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = {"classpath:facetuis.properties"})
public class ImageTest {
    @Autowired
    private InvitingService invitingService;

    @Test
    public void test(){
        invitingService.getImage("c:/2.png","5645313");
    }
}
