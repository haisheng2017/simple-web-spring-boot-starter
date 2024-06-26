package example.hao.example.controller;

import example.hao.example.mapper.UserMapper;
import example.hao.example.model.User;
import hao.simple.exception.SimpleThrower;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @Timed
    @GetMapping
    public List<User> list() {
        return userMapper.selectList(null);
    }

    @GetMapping("/exception")
    public void exception() {
        throw SimpleThrower.badRequest("This api always throw exception.");
    }
}
