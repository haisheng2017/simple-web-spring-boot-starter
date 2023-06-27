package hao.example;

import hao.example.mapper.UserMapper;
import hao.example.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class ApplicationTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {

    }

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assertions.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
        log.trace("Finished.");
        log.debug("Finished.");
        log.info("Finished.");
        log.warn("Finished.");
        log.error("Finished.");
    }

}
