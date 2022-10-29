package com.yama.demo.mysql.dao;

import com.yama.demo.mysql.pojo.Item;
import com.yama.demo.mysql.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserMapper {

    public void insert(User user);

    public void update(User user);

    public void delete(Integer id);

    public User selectOne(Integer id);

    public List<User> selectList();

}
