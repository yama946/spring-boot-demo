package com.yama.demo.mysql.dao;

import com.yama.demo.mysql.pojo.OperationLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface OperationLogMapper {

    public void insert(OperationLog operationLog);

    /**
     * 查询结果列表
     */
    public List<OperationLog> findByCondition(Map paramMap);

    /**
     * 获取符合条件的总记录数
     * @param paramMap
     * @return
     */
    public Long countByCondition(Map paramMap);

}
