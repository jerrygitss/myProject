package com.example.mybatis_plus.config.annotation;

import com.example.mybatis_plus.config.constants.EncryptTypeEnum;

import java.lang.annotation.*;

/**
 * @author 尹志强
 * @title: SensitiveFiled
 * @description: 敏感字段注解
 * @date 2022/7/22 13:44
 */
@Inherited
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SensitiveFiled {

    /**
     * 加密类型 默认 sm2
     */
    EncryptTypeEnum value() default EncryptTypeEnum.SM2;
}
