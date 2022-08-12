package com.example.mybatis_plus.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.example.mybatis_plus.config.aspect.DecryptInterceptor;
import com.example.mybatis_plus.config.aspect.EncryptInterceptor;
import com.example.mybatis_plus.service.IEncryptService;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = MysqlDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "mysqlSqlSessionFactory")
public class MysqlDataSourceConfig {
    // 精确到 master 目录，以便跟其他数据源隔离
    static final String PACKAGE = "com.example.mybatis_plus.mapper";
    static final String MAPPER_LOCATION = "classpath:mapper/*.xml";
    static final String NULL = "configuration.setCallSettersOnNulls=true";

    @Value("${spring.datasource.dynamic.datasource.mysql.url}")
    private String url;

    @Value("${spring.datasource.dynamic.datasource.mysql.username}")
    private String user;

    @Value("${spring.datasource.dynamic.datasource.mysql.password}")
    private String password;

    @Value("${spring.datasource.dynamic.datasource.mysql.driver-class-name}")
    private String driverClass;

    @Autowired
    private EncryptInterceptor encryptInterceptor;

    @Autowired
    private DecryptInterceptor decryptInterceptor;

    @Bean(name = "mysqlDataSource")
    public DataSource mysqlDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "mysqlTransactionManager")
    public DataSourceTransactionManager masterTransactionManager() {
        return new DataSourceTransactionManager(mysqlDataSource());
    }

    @Bean(name = "mysqlSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("mysqlDataSource") DataSource mysqlDataSource)
            throws Exception {
        MybatisSqlSessionFactoryBean sessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(mysqlDataSource);
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setCallSettersOnNulls(true);
        configuration.getLogImpl();
        // 驼峰命名
        configuration.setMapUnderscoreToCamelCase(true);
        sessionFactoryBean.setConfiguration(configuration);
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(MysqlDataSourceConfig.MAPPER_LOCATION));
        //分页插件配置
        sessionFactoryBean.setPlugins(mybatisPlusInterceptor(),encryptInterceptor,decryptInterceptor);
//        sessionFactoryBean.setPlugins(mybatisPlusInterceptor());
        return sessionFactoryBean.getObject();
    }

    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}

