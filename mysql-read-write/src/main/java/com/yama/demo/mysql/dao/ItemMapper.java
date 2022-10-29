package com.yama.demo.mysql.dao;

import com.yama.demo.mysql.pojo.Item;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ItemMapper {

    public void insert(Item item);

    public void update(Item item);

    public void delete(Integer id);

    public Item selectOne(Integer id);

    public List<Item> selectList();

}
