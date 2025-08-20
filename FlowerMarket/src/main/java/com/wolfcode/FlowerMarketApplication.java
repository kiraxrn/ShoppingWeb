package com.wolfcode;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wolfcode.mapper")
public class FlowerMarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowerMarketApplication.class, args);
    }

}
