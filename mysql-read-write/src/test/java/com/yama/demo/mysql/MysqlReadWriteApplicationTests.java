package com.yama.demo.mysql;

import com.yama.demo.mysql.config.ChooseDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest
public class MysqlReadWriteApplicationTests {
    @Autowired
    DataSource dataSource;

    @Autowired
    ChooseDataSource chooseDataSource;

    @Test
    public void contextLoads() {
        System.out.println(dataSource.getClass().getTypeName());
    }

}
