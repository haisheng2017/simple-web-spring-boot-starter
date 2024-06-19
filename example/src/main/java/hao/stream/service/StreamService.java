package hao.stream.service;

import hao.stream.client.CreateStreamRequest;
import hao.stream.client.CreateStreamResponse;
import hao.stream.client.StreamClient;
import hao.stream.mapper.StreamMapper;
import hao.stream.model.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class StreamService {
    @Value("${stream.live-url.prefix:}")
    private String prefix;
    @Autowired
    private StreamMapper mapper;
    @Autowired
    private StreamClient client;

    @Transactional
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

}
