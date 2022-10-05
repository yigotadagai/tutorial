package com.chen.mapper;

import com.chen.pojo.Goods;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsMapper {
    public List<Goods> findAll();
}
