package io.oicp.yorick61c;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("io.oicp.yorick61c.mapper")
public class Yorick61cApplication {

    public static void main(String[] args) {
        SpringApplication.run(Yorick61cApplication.class, args);
    }

}
