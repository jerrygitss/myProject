package com.example.mybatis_plus.service.Impl;

import com.example.mybatis_plus.exception.TestException;
import com.example.mybatis_plus.service.UserService;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author lirui
 * @Date
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    Integer i = 0;
    @Override
    @Retryable(value={TestException.class},maxAttempts=5)
    public void retryTest() {
        System.out.println("重试"+i++);

            throw new TestException("接口失败");

    }
}
