package com.example.mybatis_plus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis_plus.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
