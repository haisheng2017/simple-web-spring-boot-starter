package hao.simple.exception;

/**
 * Created by hào on 2023/6/27
 */
public class SimpleThrower {

    public static SimpleException badRequest(String message) {
        return new SimpleException(message, CodeEnum.WEB_BAD_REQUEST, 400);
    }

    public static SimpleException internalError(String message) {
        return new SimpleException(message, CodeEnum.WEB_INTERNAL_ERROR, 500);
    }
}
