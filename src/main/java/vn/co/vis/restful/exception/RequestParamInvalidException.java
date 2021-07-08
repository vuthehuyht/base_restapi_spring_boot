package vn.co.vis.restful.exception;

/**
 * @author Giang Thanh Quang
 * @since 10/28/2020
 */
public class RequestParamInvalidException extends RuntimeException {
    public RequestParamInvalidException(String message) {
        super(message);
    }
}
