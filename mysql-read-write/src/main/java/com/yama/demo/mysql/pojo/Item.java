package com.yama.demo.mysql.pojo;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * @description: 商品表
 * @date: 2022年10月26日 周三 17:41
 * @author: yama946
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    //商品id
    private Integer id;
    //商品标题
    private String title;
    //商品价格
    private Double price;
    //库存数量
    private Integer num;
    //所属类目
    private Long categoryId;
    //商品状态
    private String status;
    //商家id
    private String sellerid;
    //创建时间
    private Date createtime;
    //更新时间
    private Date updatetiem;
}
