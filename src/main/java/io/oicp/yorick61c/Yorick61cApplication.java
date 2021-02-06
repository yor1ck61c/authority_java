package io.oicp.yorick61c;

import io.oicp.yorick61c.interceptor.LoginInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@MapperScan("io.oicp.yorick61c.mapper")
public class Yorick61cApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(Yorick61cApplication.class, args);
    }


    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new LoginInterceptor());
    }

}
