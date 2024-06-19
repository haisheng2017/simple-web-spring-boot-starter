package example.hao.stream.client;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange(url = "/v2/streams")
public interface StreamClient {
    @PostExchange(contentType = MediaType.APPLICATION_JSON_VALUE, accept = MediaType.APPLICATION_JSON_VALUE)
    CreateStreamResponse add(@RequestBody CreateStreamRequest request);
}
