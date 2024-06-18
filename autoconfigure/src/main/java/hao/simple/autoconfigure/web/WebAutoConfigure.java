package hao.simple.autoconfigure.web;

import hao.simple.logging.TracingUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by hào on 2023/7/3
 */
@ConditionalOnWebApplication
@AutoConfiguration
@Import({ExceptionAdvice.class})
public class WebAutoConfigure {

    @Bean
    public FilterRegistrationBean<TraceFilter> traceFilter() {
        FilterRegistrationBean<TraceFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new TraceFilter());
        registration.addUrlPatterns("/*");
        registration.setName("traceFilter");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }


    @Slf4j
    public static class TraceFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            String traceId = request.getHeader(TracingUtils.CONST_TRACE_ID);
            if (traceId == null) {
                traceId = genTraceId(request);
            }
            TracingUtils.setTraceId(traceId);
            response.setHeader(TracingUtils.CONST_TRACE_ID, traceId);
            filterChain.doFilter(request, response);
        }

        private String genTraceId(HttpServletRequest request) {
            log.debug("{} {} x-trace-id not found.", request.getMethod(), request.getServletPath());
            String traceId = UUID.randomUUID().toString();
            log.debug("generate x-trace-id: {} ", traceId);
            return traceId;
        }
    }


}
