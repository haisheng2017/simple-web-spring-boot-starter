package example.hao.stream.client;

import hao.simple.exception.SimpleThrower;
import hao.simple.logging.TracingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.io.IOException;


/**
 * spring boot 3.2 at least
 */
public class RestClientFactory {

    @Slf4j
    private static class Log {

    }

    /**
     * @param target target must be an interface and annotated by @HttpExchange
     * @return a proxy client created by Spring "HttpInterface"
     */
    public static <T> T create(Class<T> target, String baseUrl) {
        if (!target.isInterface() || !target.isAnnotationPresent(HttpExchange.class)) {
            throw SimpleThrower.internalError("Target must be an interface and annotated by @HttpExchange");
        }
        // TODO check it out a rect client singleton based on url possibility
        RestClient restClient = RestClient.builder()
                .defaultStatusHandler(new DefaultResponseErrorHandler())
                .requestInterceptor((request, body, execution) -> {
                    if (!request.getHeaders().containsKey(TracingUtils.CONST_TRACE_ID)) {
                        request.getHeaders().add(TracingUtils.CONST_TRACE_ID, TracingUtils.getOrGenerateTraceId());
                    }
                    return execution.execute(request, body);
                })
                .requestFactory(
                        //  use apache http client to handle 3xx
                        new HttpComponentsClientHttpRequestFactory()
                )
                .baseUrl(baseUrl).build();
        return HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build().createClient(target);
    }
}
