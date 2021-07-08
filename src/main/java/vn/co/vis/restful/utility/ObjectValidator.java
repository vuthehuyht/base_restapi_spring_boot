package vn.co.vis.restful.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Validator to validate an object has annotations to validate
 *
 */
@Component
public class ObjectValidator {

    @Autowired
    @Qualifier("validator")
    LocalValidatorFactoryBean validatorFactory;

    /**
     * Validate an object then return message, Object must using ConstraintViolation to validate
     *
     * @param t   object
     * @param <T> Type
     * @return an blank string if object is valid, a detail message if object is invalid
     */
    public <T> String validateRequestThenReturnMessage(T t) {
        Set<ConstraintViolation<T>> violations = validatorFactory.getValidator().validate(t);
        List<String> messages = new ArrayList<>();
        for (ConstraintViolation<T> violation : violations) {
            messages.add(violation.getMessage());
        }
        String message = String.join(",", messages);
        if (message.contains(",")) {
            message = "[" + message + "]";
        }
        return message;
    }
}
