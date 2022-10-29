package com.yama.demo.mysql.config;

import com.yama.demo.mysql.config.ChooseDataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Aspect
@EnableAspectJAutoProxy
@Order(-9999)
public class DataSourceAspect {

    public Map<String, List<String>> methodMap = new HashMap<String, List<String>>();


    @Before("execution(* com..service.*.*(..))")
    public void beforeExecute(JoinPoint joinPoint){

        String name = joinPoint.getSignature().getName();
        System.out.println("------> 拦截的方法名 : " + name);

        for (String key : methodMap.keySet()) {
            for (String type : methodMap.get(key)) {
                if(name.startsWith(type)){
                    DataSourceHandler.putDataSource(key);
                    System.out.println("---------> 获取当前使用的数据库连接池 : " + key);
                    break;
                }
            }
        }

    }

    // 设置方法名前缀对应的数据源
    @Autowired
    public void setMethodType() {
        Map<String,String> sourceMap = new HashMap<>();
        sourceMap.put("read",",get,select,count,list,query,find");
        sourceMap.put("write",",add,create,update,delete,remove,insert");
        for (String key : sourceMap.keySet()) {
            List<String> v = new ArrayList<String>();
            String[] types = sourceMap.get(key).split(",");
            for (String type : types) {
                if (!StringUtils.isEmpty(type)) {
                    v.add(type);
                }
            }
            methodMap.put(key, v);//key -- read , write ;  value --> [get,select,count,list,query,find]
        }
        System.out.println("methodMap: "+methodMap);
    }


}
