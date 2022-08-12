package com.example.mybatis_plus.service.Impl;


import com.example.mybatis_plus.config.annotation.SensitiveFiled;
import com.example.mybatis_plus.service.IEncryptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author 尹志强
 * @title: IEncryptServiceImpl
 * @description: 加解密实现，后期可以扩展
 * @date 2022/7/22 13:52
 */
@Slf4j
@Component
public class IEncryptServiceImpl  implements IEncryptService {

    @Override
    public <T> T encrypt(Field[] fields, T paramsObject) throws Exception {
        for (Field field : fields) {
            //取出所有被EncryptDecryptFiled注解的字段
            SensitiveFiled sensitiveFiled = field.getAnnotation(SensitiveFiled.class);
            if (!Objects.isNull(sensitiveFiled)) {
                //将此对象的 accessible 标志设置为指示的布尔值。值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查。
                field.setAccessible(true);
                Object object = field.get(paramsObject);
                //这里暂时只对String类型来加密
                if (object instanceof String) {
                    String value = (String) object;
                    String encrypt = "";
                    //修改: 如果有标识则不加密，没有则加密并加上标识前缀
                    switch (sensitiveFiled.value()) {
                        case SM2:
//                            encrypt = SM2Util.encrypt(value); todo 加密需替换
                            encrypt = value + "我被加密了";
                            break;
                    }
                    //开始对字段加密使用自定义的加密
                    log.info("开始对字段加密使用自定义的加密 加密前 ： {} 加密后 ： {}", value, encrypt);
                    field.set(paramsObject, encrypt);
                }
            }
        }
        return paramsObject;
    }

    @Override
    public <T> T decrypt(T result) throws Exception {
        //取出resultType的类
        Class<?> resultClass = result.getClass();
        Field[] declaredFields = resultClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            //去除所有被EncryptDecryptFiled注解的字段
            SensitiveFiled sensitiveFiled = declaredField.getAnnotation(SensitiveFiled.class);
            if (!Objects.isNull(sensitiveFiled)) {
                //将此对象的 accessible 标志设置为指示的布尔值。值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查。
                declaredField.setAccessible(true);
                //这里的result就相当于是字段的访问器
                Object object = declaredField.get(result);
                //只支持String解密
                if (object instanceof String) {
                    String value = (String) object;
                    String encrypt = "";
                    switch (sensitiveFiled.value()) {
                        case SM2:
//                            encrypt = SM2Util.decrypt(value); todo 解密需替换
//                            encrypt = value.replaceAll("我被解密了","");
                            encrypt = value+"我被解密了";
                            break;
                    }
                    //对注解在这段进行逐一解密
                    log.info("开始对字段解密使用自定义的解密 解密前 ： {} 解密后 ： {}", value, encrypt);
                    declaredField.set(result, encrypt);
                }
            }
        }
        return result;
    }

}
