package com.example.mybatis_plus.config.aspect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 拦截配置
 * @author LEE
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {
    @Autowired
    private EncryptInterceptor encryptInterceptor;
    @Autowired
    private DecryptInterceptor decryptInterceptor;
    static final String[] ORIGINS = new String[] { "GET", "POST", "PUT", "DELETE" };
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // 设置允许跨域请求的域名
        config.addAllowedOrigin("*");
        // 是否允许证书 不再默认开启
        // config.setAllowCredentials(true);
        // 设置允许的方法
        config.addAllowedMethod("*");
        // 允许任何头
        config.addAllowedHeader("*");
        config.addExposedHeader("token");
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);
        return new CorsFilter(configSource);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        /**
         * 拦截全部路径，这个跨域需要放在最上面
         */
//        registry.addInterceptor(corsInterceptor()).addPathPatterns("/**");
//
//        registry.addInterceptor(authInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/xnft/auth/**")
//                .excludePathPatterns("/xnft/noLogin/**")
//                .excludePathPatterns("/xnft/function/**")
//                .excludePathPatterns("/xnft/config/**")
//                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");

//            registry.addInterceptor(encryptInterceptor).addPathPatterns("/**");


    }

    /***
     * 配置静态资源访问拦截
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }
//
//    @Bean
//    CorsInterceptor corsInterceptor() {
//        return new CorsInterceptor();
//    }
//
//    @Bean
//    AuthInterceptor authInterceptor() {
//        return new AuthInterceptor();
//    }

}
