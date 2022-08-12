package com.example.mybatis_plus.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.mybatis_plus.entity.User;
import com.example.mybatis_plus.mapper.UserMapper;
import com.example.mybatis_plus.service.Impl.UserServiceImpl;
import com.example.mybatis_plus.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test/user")
@AllArgsConstructor
@Slf4j
public class UserController {

    private UserServiceImpl userService;
    private  UserMapper userMapper;
    @PostMapping("/list")
    public List<User> listAjjbxx(@RequestParam("user") String user){

        log.info(user);

//        List<User> userList = userMapper.selectList(Wrappers.<User>lambdaQuery().eq(User::getId, 3));

        userService.retryTest();

        return new ArrayList<>();
    }
}
