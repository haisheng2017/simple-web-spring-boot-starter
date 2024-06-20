package hao.simple.autoconfigure.web;

import hao.simple.autoconfigure.web.resp.ExceptionResponse;
import hao.simple.autoconfigure.web.utils.WebUtils;
import hao.simple.exception.CodeEnum;
import hao.simple.exception.SimpleException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by hào on 2023/7/4
 */
@Slf4j
@ControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(SimpleException.class)
    public ResponseEntity<ExceptionResponse> simple(HttpServletRequest request, SimpleException ex) {
        if (ex.getTraceId() == null) {
            String traceId = WebUtils.getTraceId(request);
            ex.setTraceId(traceId);
        }
        return new ResponseEntity<>(new ExceptionResponse(ex), HttpStatusCode.valueOf(ex.getHttpStatus()));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ExceptionResponse> exception(HttpServletRequest request, HandlerMethodValidationException e) {
        String msg = e.getAllValidationResults().stream().map(ParameterValidationResult::getResolvableErrors)
                .flatMap(List::stream)
                .map(MessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));
        SimpleException ex = new SimpleException(msg, CodeEnum.WEB_BAD_REQUEST, 400);
        return simple(request, ex);
    }

    @ExceptionHandler(RestClientResponseException.class)
    public ResponseEntity<ExceptionResponse> exception(HttpServletRequest request, RestClientResponseException e) {
        SimpleException ex = new SimpleException(e.getResponseBodyAsString(),
                CodeEnum.WEB_INTERNAL_CALL_ERROR, e.getStatusCode().value());
        return simple(request, ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> exception(HttpServletRequest request, Exception e) {
        log.error("An unknown occurred.", e);
        SimpleException ex = new SimpleException(e.getMessage(), CodeEnum.UNKNOWN, 500);
        return simple(request, ex);
    }
}
