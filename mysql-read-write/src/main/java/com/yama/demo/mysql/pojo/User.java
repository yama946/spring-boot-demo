package com.yama.demo.mysql.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description: 用户表
 * @date: 2022年10月26日 周三 17:49
 * @author: yama946
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    //用户id
    private Integer id;

    private String username;

    private String password;

    private String name;

    private Date brithday;

    private Character sex;

    private String email;

    private String phone;

    private String qq;
}
