package com.km086.server.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductParseServiceTest {

    @Autowired
    ProductParseService productParseService;

    @Test
    public void parseExcel() {
        try{
            InputStream is = new FileInputStream(new File("C:\\Users\\Lenovo\\Documents\\商户上传菜单.xlsx"));
            productParseService.parse(new Long(4009548), is);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
