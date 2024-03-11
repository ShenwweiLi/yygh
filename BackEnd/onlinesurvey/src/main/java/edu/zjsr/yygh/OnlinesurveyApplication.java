package edu.zjsr.yygh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@EnableOpenApi
@SpringBootApplication
@ComponentScan(basePackages = "edu.zjsr")
@MapperScan("edu.zjsr.yygh.mapper")
public class OnlinesurveyApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlinesurveyApplication.class, args);
    }

}
