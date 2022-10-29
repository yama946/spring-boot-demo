package com.yama.demo.mysql.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 操作记录表
 * @date: 2022年10月26日 周三 17:52
 * @author: yama946
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationLog {
    private Long id;
    //操作类
    private String operateClass;
    //操作方法
    private String operateMethod;
    //返回值类型
    private String returnClass;
    //操作用户
    private String operateUser;
    //操作时间
    private String operateTime;
    //请求参数名及参数值
    private String paramAndValue;
    //执行方法耗时, 单位 ms
    private Long costTime;
    //返回值
    private String returnValue;
}
