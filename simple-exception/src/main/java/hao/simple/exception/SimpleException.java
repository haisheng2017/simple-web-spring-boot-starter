package hao.simple.exception;

import hao.simple.logging.TracingUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by hào on 2023/6/27
 */
@Getter
public class SimpleException extends RuntimeException {

    private final String code;
    private final int httpStatus;
    @Setter
    private String traceId;

    public SimpleException(String message, CodeEnum code, int httpStatus) {
        super(message);
        this.code = code.name();
        this.httpStatus = httpStatus;
        traceId = TracingUtils.getTraceId() == null ? "" : TracingUtils.getTraceId();
    }

}
