package com.yama.demo.mysql.dao;

import com.yama.demo.mysql.pojo.Brand;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface BrandMapper {

    public void insert(Brand brand);

    public void update(Brand brand);

    public void delete(Integer id);

    public Brand selectOne(Integer id);

    public List<Brand> selectList();

}
