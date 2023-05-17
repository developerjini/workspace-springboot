package com.developerjini.jsp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface HomeMapper {
  @Select("select now()")
  String getTime();
}
