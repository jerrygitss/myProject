package com.example.mybatis_plus.config.aspect;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.example.mybatis_plus.service.IEncryptService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author 尹志强
 * @title: DecryptInterceptor
 * @description: 数据解密拦截器
 * @date 2022/7/22 10:11
 */
@Slf4j
@Component
@Intercepts({
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})
})
public class DecryptInterceptor implements Interceptor {

    @Autowired
    private IEncryptService encryptService;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //取出查询的结果
        Object resultObject = invocation.proceed();
        if (Objects.isNull(resultObject)) {
            return null;
        }
        //基于selectList
        if (resultObject instanceof ArrayList) {
            ArrayList resultList = (ArrayList) resultObject;
            if (!CollectionUtils.isEmpty(resultList)) {
                for (Object result : resultList) {
                    //逐一解密
                    encryptService.decrypt(result);
                    log.info("{}",result);
                }
            }
            //基于selectOne
        } else {
            encryptService.decrypt(resultObject);
        }
        return resultObject;
    }
//    @Override
//    public Object plugin(Object target){
//        return Plugin.wrap(target,this);
//    }
}
