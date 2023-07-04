package hao.simple.autoconfigure.web.resp;

import hao.simple.exception.SimpleException;
import lombok.Getter;

/**
 * Created by hào on 2023/7/4
 */
@Getter
public class ExceptionResponse {

    private final String code;
    private final int httpStatus;
    private final String traceId;
    private final String message;

    public ExceptionResponse(SimpleException ex) {
        code = ex.getCode();
        httpStatus = ex.getHttpStatus();
        traceId = ex.getTraceId();
        message = ex.getMessage();
    }
}
