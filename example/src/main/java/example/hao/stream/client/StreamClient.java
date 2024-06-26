package example.hao.stream.client;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange(url = "/v2/streams", contentType = MediaType.APPLICATION_JSON_VALUE, accept = MediaType.APPLICATION_JSON_VALUE)
public interface StreamClient {
    @PostExchange
    CreateStreamResponse add(@RequestBody CreateStreamRequest request);

    @DeleteExchange(url = "/{id}")
    void delete(@PathVariable String id);

    @PostExchange(url = "/{id}/skill")
    void addSkill(@PathVariable String id, @RequestBody Object request);

    @DeleteExchange(url = "/{id}/skill/{skillId}")
    void deleteSkill(@PathVariable String id, @PathVariable String skillId);

}
