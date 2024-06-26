package example.hao.stream.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import example.hao.stream.mapper.StreamMapper;
import example.hao.stream.model.Stream;
import example.hao.stream.service.StreamService;
import hao.simple.exception.SimpleThrower;
import io.micrometer.common.util.StringUtils;
import io.micrometer.core.annotation.Timed;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.ibatis.annotations.Delete;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/streams/skills")
public class StreamSkillController {
    @Autowired
    private StreamMapper streamMapper;
    @Autowired
    private StreamService service;

    @Timed("streams.skill")
    @PostMapping
    public void add(@RequestParam(required = false) Integer id) {
        List<String> streamIds = new ArrayList<>();
        if (id != null) {
            Stream s = streamMapper.selectById(id);
            if (s == null) {
                throw SimpleThrower.badRequest("Unknown id: " + id);
            }
            streamIds.add(s.getStreamId());
        } else {
            streamIds.addAll(streamMapper.selectList(new QueryWrapper<>()).stream().map(Stream::getStreamId).toList());
        }
        for (String sid : streamIds) {
            try {
                service.addSkill(sid);
            } catch (Exception ignored) {

            }
        }
    }

    @Timed("streams.skill.delete")
    @DeleteMapping
    public void delete(@RequestParam String skillId) {
        service.deleteSkill(skillId);
    }
}
