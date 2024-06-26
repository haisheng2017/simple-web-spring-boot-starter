package hao.simple.autoconfigure.web;

import ch.qos.logback.access.servlet.TeeFilter;
import ch.qos.logback.access.tomcat.LogbackValve;
import hao.simple.logging.TracingUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.embedded.tomcat.ConfigurableTomcatWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Created by hào on 2023/7/3
 */
@ConditionalOnWebApplication
@AutoConfiguration
@Import({ExceptionAdvice.class})
public class WebAutoConfigure {

    @Value("#{'${simple.web.trace.exclude-urls:}'.split(',')}")
    private List<String> excludeUrls;

    @Bean
    public FilterRegistrationBean<TraceFilter> traceFilter() {
        FilterRegistrationBean<TraceFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new TraceFilter(excludeUrls));
        registration.addUrlPatterns("/*");
        registration.setName("traceFilter");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }


    @Configuration(proxyBeanMethods = false)
    @ConditionalOnExpression("#{${simple.logging.console.access.enabled:true} ||" +
            "!'${simple.logging.access-log-file-path:}'.isEmpty() ||" +
            "!'${simple.logging.access-debug-log-file-path:}'.isEmpty()" +
            "}")
    public static class AccessLog {
        @Bean
        public FilterRegistrationBean<TeeFilter> teeFilter() {
            FilterRegistrationBean<TeeFilter> registration = new FilterRegistrationBean<>();
            registration.setFilter(new TeeFilter());
            registration.addUrlPatterns("/*");
            registration.setName("teeFilter");
            registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
            return registration;
        }

        @Bean
        public WebServerFactoryCustomizer<ConfigurableTomcatWebServerFactory> tomcatCustomizer() {
            return factory -> factory.addEngineValves(new LogbackValve());
        }
    }

    @Slf4j
    @AllArgsConstructor
    public static class TraceFilter extends OncePerRequestFilter {

        private final List<String> exclusions;

        @Override
        protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
            // TODO support pattern
            return exclusions.contains(request.getRequestURI());
        }

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            String traceId = request.getHeader(TracingUtils.CONST_TRACE_ID);
            if (traceId == null) {
                traceId = genTraceId(request);
            }
            response.setHeader(TracingUtils.CONST_TRACE_ID, traceId);
            filterChain.doFilter(request, response);
        }

        private String genTraceId(HttpServletRequest request) {
            log.debug("{} {} x-trace-id not found.", request.getMethod(), request.getServletPath());
            String traceId = TracingUtils.getOrGenerateTraceId();
            log.debug("generate x-trace-id: {} ", traceId);
            return traceId;
        }
    }


}
