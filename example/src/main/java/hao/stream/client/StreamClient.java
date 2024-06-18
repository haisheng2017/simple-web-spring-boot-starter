package hao.stream.client;

import lombok.Getter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;


/**
 * spring boot 3.2 at least
 */
public class StreamClient {
    @Getter
    private final Service service;

    public StreamClient(String baseUrl) {
        RestClient restClient = RestClient.builder().baseUrl(baseUrl).build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        service = factory.createClient(Service.class);
    }


    @HttpExchange(url = "/v2/streams")
    public interface Service {
        @PostExchange
        CreateStreamResponse add(@RequestBody CreateStreamRequest request);
    }

}
