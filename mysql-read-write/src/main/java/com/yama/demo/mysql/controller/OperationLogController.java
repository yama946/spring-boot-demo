package com.yama.demo.mysql.controller;

import com.yama.demo.mysql.pojo.PageResult;
import com.yama.demo.mysql.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/operationLog")
public class OperationLogController {

    @Autowired
    private OperationLogService operationLogService;

    @RequestMapping("/findByPage")
    public PageResult findByPage(@RequestBody Map paramMap , Integer pageNum , Integer rows){
        return operationLogService.findByPage(paramMap,pageNum,rows);
    }

}
