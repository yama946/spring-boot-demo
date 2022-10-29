package com.yama.demo.mysql.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @date: 2022年10月26日 周三 17:27
 * @author: yama946
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
    private Long id;

    //品牌名称
    private String name;

    //品牌首字母
    private String firstChar;
}
