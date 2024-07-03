package example.hao.stream.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import example.hao.stream.client.CreateStreamRequest;
import example.hao.stream.client.CreateStreamResponse;
import example.hao.stream.client.StreamClient;
import example.hao.stream.mapper.StreamMapper;
import example.hao.stream.model.Stream;
import hao.simple.exception.SimpleThrower;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientResponseException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
public class StreamService {

    private final ObjectMapper jc = new ObjectMapper();
    private final Random random = new Random();
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

    public void addSkill(String streamId, int times) {
        try {
            Map<String, Object> request = jc.readValue(new File(requestPath), new TypeReference<>() {
            });
            String rd = String.valueOf(random.nextInt(100) >= 75 ? 0 : 100);
            String data = (String) request.get("data");
            request.put("data", data.replaceAll("REGPAT", UUID.randomUUID().toString())
                    .replaceAll("REG_COND_PAT", rd));
            log.warn("Generate RD: {}", rd);
            for (int i = 1; i <= times; i++) {
                request.put("version", String.valueOf(i));
                client.addSkill(streamId, request);
            }
        } catch (IOException | RestClientResponseException e) {
            log.warn("add skill exception.", e);
            throw SimpleThrower.internalError(e.getMessage());
        }
    }

    public void deleteSkill(String skillId) {
        List<Stream> streams = mapper.selectList(new QueryWrapper<>());
        for (Stream s : streams) {
            try {
                client.deleteSkill(s.getStreamId(), skillId);
            } catch (Exception e) {
                log.warn("delete skill exception.", e);
            }
        }
    }

}
