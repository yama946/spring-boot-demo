package com.yama.demo.rabbitmq.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description:
 * @date: 2022年10月13日 周四 16:33
 * @author: yama946
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Serializable {
    private String name;

    private Integer age;

    private Double height;

    private Double weight;

    private String address;
}
