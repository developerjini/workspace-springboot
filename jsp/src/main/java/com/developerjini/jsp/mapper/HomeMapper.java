package com.developerjini.jsp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface HomeMapper {
  @Select("select now()")
  String getTime();

  @Select("select * from tbl_sample")
  List<Map<String, Object>> getList();
}
