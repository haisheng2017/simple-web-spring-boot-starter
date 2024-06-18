package hao.stream.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import hao.simple.exception.SimpleThrower;
import hao.stream.mapper.StreamMapper;
import hao.stream.model.Stream;
import hao.stream.service.StreamService;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/streams")
public class StreamController {
    @Autowired
    private StreamMapper streamMapper;
    @Autowired
    private StreamService service;

    @Timed("streams.list")
    @GetMapping
    public List<Stream> list() {
        return streamMapper.selectList(null);
    }

    @Timed("streams.create")
    @PostMapping
    public void create() {
        Stream entity = new Stream();
        entity.setStreamId("test");
        entity.setStreamUrl("test");
        entity.setCreateTime(System.currentTimeMillis());
        streamMapper.insert(entity);
    }

    @Timed("streams.sync")
    @PostMapping("/sync")
    public void sync(@RequestParam Integer total) {
        service.sync(1, total);
    }

    @Timed("streams.deleteAll")
    @DeleteMapping
    public void deleteAll() {
        streamMapper.delete(
                new QueryWrapper<>()
        );
    }

    @GetMapping("/exception")
    public void exception() {
        throw SimpleThrower.badRequest("This api always throw exception.");
    }
}
