package hao.simple.autoconfigure.web.utils;

import hao.simple.logging.TracingUtils;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Created by hào on 2023/7/4
 */
public class WebUtils {

    public static String getTraceId(HttpServletRequest request) {
        return request.getHeader(TracingUtils.CONST_TRACE_ID);
    }
}
