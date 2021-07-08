package vn.co.vis.restful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vn.co.vis.restful.exception.ResourceNotFoundException;
import vn.co.vis.restful.log.AppLogger;
import vn.co.vis.restful.log.LoggerFactory;
import vn.co.vis.restful.log.LoggerType;

import java.util.Optional;

/**
 * Common attribute and method for controller
 * Using to mapping request url
 * @author Giang Thanh Quang
 * @since 10/15/2020
 */
public abstract class AbstractController<S> {
    @Autowired
    protected S service;

    protected static final AppLogger APP_LOGGER = LoggerFactory.getLogger(LoggerType.APPLICATION);

    /**
     * @param response Optional of response object
     * @param <T>      Class type
     * @return ResponseEntity
     */
    protected <T> ResponseEntity<T> response(Optional<T> response) {
        return new ResponseEntity<T>(response.orElseThrow(() -> {
            throw new ResourceNotFoundException();
        }), HttpStatus.OK);
    }
}
