package com.example.mybatis_plus.config.aspect;

import com.example.mybatis_plus.service.IEncryptService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.plugin.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;

/**
 * @author 尹志强
 * @title: EncryptInterceptor
 * @description: 数据保存加密拦截器
 * @date 2022/7/22 9:58
 */
@Slf4j
@Component
@Intercepts({
        @Signature(type = ParameterHandler.class, method = "setParameters", args = PreparedStatement.class),
})
public class EncryptInterceptor implements Interceptor {

    @Autowired
    private IEncryptService encryptService;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //@Signature 指定了 type= parameterHandler 后，这里的 invocation.getTarget() 便是parameterHandler
        //若指定ResultSetHandler ，这里则能强转为ResultSetHandler
        ParameterHandler parameterHandler = (ParameterHandler) invocation.getTarget();
        //获取参数对象，即mapper中paramsType的实例
        Field paramsFiled = parameterHandler.getClass().getDeclaredField("parameterObject");
        //将此对象的 accessible 标志设置为指示的布尔值。值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查。
        paramsFiled.setAccessible(true);
        //取出实例
        Object parameterObject = paramsFiled.get(parameterHandler);
        if (parameterObject != null) {
            Class<?> parameterObjectClass = parameterObject.getClass();
            //取出当前类的所有字段，传入加密方法
            Field[] declaredFields = parameterObjectClass.getDeclaredFields();
            encryptService.encrypt(declaredFields, parameterObject);
        }
        //获取原方法的返回值
        return invocation.proceed();
    }
    @Override
    public Object plugin(Object target){
        return Plugin.wrap(target,this);
    }
}
