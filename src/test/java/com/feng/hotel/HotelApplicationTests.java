package com.feng.hotel;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@ActiveProfiles(value = "dev")
@SpringBootTest
@RunWith(SpringRunner.class)
public class HotelApplicationTests {

    @Test
    public void contextLoads() {
    }

}
