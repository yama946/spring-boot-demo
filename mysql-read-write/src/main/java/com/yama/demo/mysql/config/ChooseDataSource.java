package com.yama.demo.mysql.config;

import org.aspectj.lang.annotation.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 选择数据源
 */
@ConfigurationProperties("data")
@Configuration("dataSource")
@AutoConfigureAfter(DataSourcesConfig.class)
public class ChooseDataSource extends AbstractRoutingDataSource {
    @Resource(name = "readDataSource")
    DataSource readDataSource;

    @Resource(name = "writeDataSource")
    DataSource writeDataSource;


    /**
     * 实现父类中的抽象方法，获取数据源名称
     * @return
     */
    protected Object determineCurrentLookupKey() {
        return DataSourceHandler.getDataSource();
    }

    @Autowired
    public void setSuperTargetDataSources(){
        Map<Object,Object> target = new HashMap<>();
        target.put("read",readDataSource);
        target.put("write",writeDataSource);
        super.setTargetDataSources(target);
        super.setDefaultTargetDataSource(writeDataSource);
    }



}
