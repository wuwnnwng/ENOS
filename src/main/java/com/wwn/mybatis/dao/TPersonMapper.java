package com.wwn.mybatis.dao;

import com.wwn.mybatis.entity.TPerson;
import java.util.List;
 
public interface TPersonMapper {
    int insert(TPerson record);

    List<TPerson> selectAll();
}