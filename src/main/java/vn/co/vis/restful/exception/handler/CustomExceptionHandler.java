package vn.co.vis.restful.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vn.co.vis.restful.dto.response.ErrorResponse;
import vn.co.vis.restful.exception.RequestParamInvalidException;
import vn.co.vis.restful.exception.ResourceNotFoundException;
import vn.co.vis.restful.exception.TokenInvalidException;
import vn.co.vis.restful.log.AppLogger;
import vn.co.vis.restful.log.LoggerFactory;
import vn.co.vis.restful.log.LoggerType;

/**
 * Exception handler for application
 * @author Giang Thanh Quang
 * @since 10/28/2020
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private static final AppLogger APP_LOGGER = LoggerFactory.getLogger(LoggerType.APPLICATION);

    @ExceptionHandler({TokenInvalidException.class})
    public ResponseEntity<ErrorResponse> handleTokenInvalidException(TokenInvalidException e) {
        APP_LOGGER.error(e.getMessage());
        return new ResponseEntity<>(new ErrorResponse(e.getCode(), e.getMessage()), null, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({RequestParamInvalidException.class})
    public ResponseEntity<ErrorResponse> handleTokenInvalidException(RequestParamInvalidException e) {
        APP_LOGGER.error(e.getMessage());
        return new ResponseEntity<>(new ErrorResponse("E11", e.getMessage()), null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException e) {
        APP_LOGGER.error(e.getMessage());
        return new ResponseEntity<>(new ErrorResponse("D0", e.getMessage()), null, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> handleCommonException(Exception e) {
        APP_LOGGER.error(e.getMessage());
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponse("E0", e.getMessage()), null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
