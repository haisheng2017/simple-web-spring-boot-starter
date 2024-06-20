package example.hao.stream.controller;

import example.hao.stream.mapper.StreamMapper;
import example.hao.stream.model.Stream;
import example.hao.stream.service.StreamService;
import io.micrometer.core.annotation.Timed;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/streams")
public class StreamController {
    @Autowired
    private StreamMapper streamMapper;
    @Autowired
    private StreamService service;

    @GetMapping
    public List<Stream> list() {
        return streamMapper.selectList(null);
    }

    @Timed("streams.sync")
    @PostMapping("/sync")
    public void sync(
            @RequestParam(required = false, defaultValue = "1") @Range(min = 1, max = 100, message = "from should in [1,100]") Integer from,
            @RequestParam @Range(min = 1, max = 100, message = "to should in [1,100]") Integer to
    ) {
        service.sync(from, to);
    }

    @DeleteMapping
    public void deleteAll(
            @RequestParam(required = false, defaultValue = "1") @Range(min = 1, max = 100, message = "from should in [1,100]") Integer from,
            @RequestParam(required = false, defaultValue = "100") @Range(min = 1, max = 100, message = "to should in [1,100]") Integer to
    ) {
        service.delete(from,to);
    }
}
