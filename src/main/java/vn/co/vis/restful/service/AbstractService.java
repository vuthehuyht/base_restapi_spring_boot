package vn.co.vis.restful.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import vn.co.vis.restful.log.AppLogger;
import vn.co.vis.restful.log.LoggerFactory;
import vn.co.vis.restful.log.LoggerType;
import vn.co.vis.restful.utility.ObjectValidator;

import javax.annotation.PostConstruct;

/**
 * Common attribute and method for service
 * Use to implement business logic
 * @author Giang Thanh Quang
 * @since 10/15/2020
 */
public abstract class AbstractService {
    @Autowired
    Environment env;

    protected static final AppLogger APP_LOGGER = LoggerFactory.getLogger(LoggerType.APPLICATION);

    @Autowired
    protected ObjectValidator validator;


    protected ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
