package example.hao.stream.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import example.hao.stream.client.CreateStreamRequest;
import example.hao.stream.client.CreateStreamResponse;
import example.hao.stream.client.StreamClient;
import example.hao.stream.mapper.StreamMapper;
import example.hao.stream.model.Stream;
import hao.simple.exception.SimpleThrower;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class StreamService {

    private final ObjectMapper jc = new ObjectMapper();
    @Value("${stream.live-url.prefix:}")
    private String prefix;
    @Value("${stream.skill-request-json:}")
    private String requestPath;
    @Autowired
    private StreamMapper mapper;
    @Autowired
    private StreamClient client;

    public void sync(int from, int to) {
        for (int i = from; i <= to; i++) {
            Stream stream = new Stream();
            stream.setStreamUrl(prefix + i);
            CreateStreamResponse resp = client.add(new CreateStreamRequest(stream.getStreamUrl()));
            log.debug("Return stream id is: {}", resp.getId());
            stream.setStreamId(resp.getId());
            mapper.insert(stream);
        }
    }

    public void delete(Integer from, Integer to) {
        List<Stream> sl = mapper.selectList(
                new QueryWrapper<Stream>()
                        .ge("id", from)
                        .le("id", to)
        );

        for (Stream s : sl) {
            try {
                client.delete(s.getStreamId());
                mapper.deleteById(s.getId());
            } catch (Exception e) {
                log.warn("delete stream  exception:{}.", s.getStreamId(), e);
            }
        }
    }

    public void addSkill(String streamId) {
        try {
//            String json = FileUtils.readFileToString(new File(requestPath), StandardCharsets.UTF_8);
            Object request = jc.readValue(new File(requestPath), Object.class);
            client.addSkill(streamId, request);
        } catch (IOException e) {
            log.warn("add skill exception.", e);
            throw SimpleThrower.internalError(e.getMessage());
        }
    }

}
