package com.example.mybatis_plus.service;

import java.lang.reflect.Field;

/**
 * @author 尹志强
 * @title: IEncryptService
 * @description: 加密
 * @date 2022/7/22 13:48
 */
public interface IEncryptService {

    /**
     * 加密
     * @param fields 加密字段
     * @param paramsObject
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> T encrypt(Field[] fields, T paramsObject) throws Exception;

    /**
     * 解密
     * @param result 解密信息
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> T decrypt(T result) throws Exception;

}
