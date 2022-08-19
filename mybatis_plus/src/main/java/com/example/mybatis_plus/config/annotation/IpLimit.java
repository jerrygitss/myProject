package com.example.mybatis_plus.config.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author lr bullock
 */

@Retention(RUNTIME)
@Target(METHOD)
public @interface IpLimit {

    String limitCount();

    String limitTime();
}
