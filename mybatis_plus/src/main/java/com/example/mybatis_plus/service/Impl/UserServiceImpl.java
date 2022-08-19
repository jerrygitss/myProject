package com.example.mybatis_plus.service.Impl;

import com.example.mybatis_plus.exception.TestException;
import com.example.mybatis_plus.service.UserService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.crypto.SecretKey;
import java.util.Base64;

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

    public static void main(String[] args) {
        String key = "1234567890_1234567890_1234567890";
        String encode = new BASE64Encoder().encode(key.getBytes());
        System.out.println(encode);
        SecretKey secretKey = Keys.hmacShaKeyFor(encode.getBytes());
        String data = "{\"user\":123}";
        String compact = Jwts.builder().setSubject(data).signWith(secretKey).compact();
        System.out.println(compact);

    }
}
