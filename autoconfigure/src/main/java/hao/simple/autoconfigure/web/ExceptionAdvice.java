package hao.simple.autoconfigure.web;

import hao.simple.autoconfigure.web.resp.ExceptionResponse;
import hao.simple.autoconfigure.web.utils.WebUtils;
import hao.simple.exception.CodeEnum;
import hao.simple.exception.SimpleException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by hào on 2023/7/4
 */
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> exception(HttpServletRequest request, Exception e) {
        SimpleException ex = new SimpleException(e.getMessage(), CodeEnum.WEB_INTERNAL_ERROR, 500);
        return simple(request, ex);
    }
}
