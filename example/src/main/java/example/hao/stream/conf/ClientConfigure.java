package example.hao.stream.conf;

import example.hao.stream.client.RestClientFactory;
import example.hao.stream.client.StreamClient;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by hào on 2023/9/5
 */
@Configuration
public class ClientConfigure {

    @Value("${stream.base-url:}")
    private String url;

    @Bean
    public StreamClient streamClient() {
        return RestClientFactory.create(StreamClient.class, url);
    }
}
