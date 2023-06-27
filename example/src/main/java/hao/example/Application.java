package hao.example;

import hao.example.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@MapperScan(basePackageClasses = {UserMapper.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.trace("Started.");
        log.debug("Started.");
        log.info("Started.");
        log.warn("Started.");
        log.error("Started.");
    }

}
