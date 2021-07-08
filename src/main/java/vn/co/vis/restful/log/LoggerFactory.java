package vn.co.vis.restful.log;

import org.apache.logging.log4j.ThreadContext;

import java.util.Map;

/**
 * @author Giang Thanh Quang
 * @since 11/19/2020
 */
public class LoggerFactory {

    public static synchronized AppLogger getLogger(LoggerType type) {
        return getLogger(type, null);
    }

    public static synchronized AppLogger getLogger(LoggerType type, Map<String, String> parameters) {
        if (parameters != null) {
            ThreadContext.putAll(parameters);
        }
        return new AppLogger(type);
    }

}
