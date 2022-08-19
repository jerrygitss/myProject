package com.example.mybatis_plus.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.mybatis_plus.entity.User;
import com.example.mybatis_plus.exception.TestException;
import com.example.mybatis_plus.mapper.UserMapper;
import com.example.mybatis_plus.service.Impl.UserServiceImpl;
import com.example.mybatis_plus.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/test/user")
@AllArgsConstructor
@Slf4j
public class UserController {

    private UserServiceImpl userService;
    private static ConcurrentHashMap HASH_MAP = new ConcurrentHashMap();
    private  UserMapper userMapper;
    @PostMapping("/list")
    public List<User> listAjjbxx(@RequestParam("user") String user){

        log.info(user);

//        List<User> userList = userMapper.selectList(Wrappers.<User>lambdaQuery().eq(User::getId, 3));

//        userService.retryTest();
        try{
            synchronized (this){
               if (HASH_MAP.containsKey(user)){
                   throw  new TestException("不能重复请求");
               }else {
                   HASH_MAP.put(user,System.currentTimeMillis());
               }
            }
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            HASH_MAP.remove(user);
        }
        HashMap<Object, Object> map = new HashMap<>(2);

        return new ArrayList<>();
    }

    public static void main(String[] args) {

        HashMap<Object, Object> map = new HashMap<>(2);
    }
}
