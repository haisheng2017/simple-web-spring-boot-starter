package hao.simple.web.spring.boot.starter;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@MapperScan("hao.simple.web.spring.boot.starter.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.debug("Started.");
        log.info("Started.");
        log.warn("Started.");
        log.error("Started.");
    }

}
