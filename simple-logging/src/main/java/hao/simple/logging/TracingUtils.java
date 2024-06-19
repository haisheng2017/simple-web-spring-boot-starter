package hao.simple.logging;

import org.codehaus.commons.compiler.util.StringUtil;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * Created by hào on 2023/6/27
 */
public class TracingUtils {

    public static final String CONST_TRACE_ID = "x-trace-id";

    public static void setTraceId(String traceId) {
        MDC.put(CONST_TRACE_ID, traceId);
    }

    public static String getTraceId() {
        return MDC.get(CONST_TRACE_ID);
    }

    public static String getOrGenerateTraceId() {
        String cur = getTraceId();
        if (cur == null || cur.length() == 0) {
            MDC.put(CONST_TRACE_ID, UUID.randomUUID().toString());
        }
        return getTraceId();
    }

}
