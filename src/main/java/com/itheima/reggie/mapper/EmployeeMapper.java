package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entiy.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper用来操作数据库（CURD）,即增删改查。
* */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
