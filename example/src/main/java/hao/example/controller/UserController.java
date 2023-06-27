package hao.example.controller;

import hao.example.mapper.UserMapper;
import hao.example.model.User;
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

    @GetMapping
    public List<User> list() {
        return userMapper.selectList(null);
    }
}
