package com.yama.demo.mysql.service;


import com.yama.demo.mysql.dao.OperationLogMapper;
import com.yama.demo.mysql.pojo.OperationLog;
import com.yama.demo.mysql.pojo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OperationLogService {

    @Autowired
    private OperationLogMapper operationLogMapper;

    public void insert(OperationLog operationLog){
        operationLogMapper.insert(operationLog);
    }

    public PageResult findByPage(Map paramMap , Integer pageNum , Integer rows){
        if(paramMap ==null){
            paramMap = new HashMap();
        }
        paramMap.put("start" , (pageNum-1)*rows);
        paramMap.put("rows",rows);

        Object costTime = paramMap.get("costTime");
        if(costTime != null){
            if("".equals(costTime.toString())){
                paramMap.put("costTime",null);
            }else{
                paramMap.put("costTime",new Long(costTime.toString()));
            }
        }

        long start_time = System.currentTimeMillis();
        Long count = operationLogMapper.countByCondition(paramMap);
        long end_time = System.currentTimeMillis();
        System.out.println("Count Cost Time : " + (end_time - start_time) + " ms");

        List<OperationLog> list = operationLogMapper.findByCondition(paramMap);
        long end_time2 = System.currentTimeMillis();
        System.out.println("Query Cost Time : " + (end_time2 - end_time) + " ms");

        return new PageResult(count,list);
    }
}
