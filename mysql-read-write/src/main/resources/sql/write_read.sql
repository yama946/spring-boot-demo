CREATE DATABASE IF NOT EXISTS write_read DEFAULT CHARACTER SET utf8mb4;

use write_read;

CREATE TABLE `brand` (
                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                         `name` varchar(255) DEFAULT NULL COMMENT '品牌名称',
                         `first_char` varchar(1) DEFAULT NULL COMMENT '品牌首字母',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `item` (
                        `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品id',
                        `title` varchar(100) NOT NULL COMMENT '商品标题',
                        `price` double(10,2) NOT NULL COMMENT '商品价格，单位为：元',
                        `num` int(10) NOT NULL COMMENT '库存数量',
                        `categoryid` bigint(10) NOT NULL COMMENT '所属类目，叶子类目',
                        `status` varchar(1) DEFAULT NULL COMMENT '商品状态，1-正常，2-下架，3-删除',
                        `sellerid` varchar(50) DEFAULT NULL COMMENT '商家ID',
                        `createtime` datetime DEFAULT NULL COMMENT '创建时间',
                        `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品表';



CREATE TABLE `user` (
                        `id` int(11) NOT NULL AUTO_INCREMENT,
                        `username` varchar(45) NOT NULL,
                        `password` varchar(96) NOT NULL,
                        `name` varchar(45) NOT NULL,
                        `birthday` datetime DEFAULT NULL,
                        `sex` char(1) DEFAULT NULL,
                        `email` varchar(45) DEFAULT NULL,
                        `phone` varchar(45) DEFAULT NULL,
                        `qq` varchar(32) DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `operation_log` (
                                 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                 `operate_class` varchar(200) DEFAULT NULL COMMENT '操作类',
                                 `operate_method` varchar(200) DEFAULT NULL COMMENT '操作方法',
                                 `return_class` varchar(200) DEFAULT NULL COMMENT '返回值类型',
                                 `operate_user` varchar(20) DEFAULT NULL COMMENT '操作用户',
                                 `operate_time` varchar(20) DEFAULT NULL COMMENT '操作时间',
                                 `param_and_value` varchar(500) DEFAULT NULL COMMENT '请求参数名及参数值',
                                 `cost_time` bigint(20) DEFAULT NULL COMMENT '执行方法耗时, 单位 ms',
                                 `return_value` varchar(200) DEFAULT NULL COMMENT '返回值',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;