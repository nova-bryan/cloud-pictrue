package com.yb.cloudPictrueBackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.yb.cloudpictruebackend.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
public class CloudPictrueBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudPictrueBackendApplication.class, args);
    }

}
