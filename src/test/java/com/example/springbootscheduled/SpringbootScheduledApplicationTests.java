package com.example.springbootscheduled;

import com.example.springbootscheduled.compareFiles.ReadFiles;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootScheduledApplicationTests {

    @Autowired
    private ReadFiles readFiles;

    @Test
    public void comparesTest() {

        readFiles.compareFile();
    }

}
