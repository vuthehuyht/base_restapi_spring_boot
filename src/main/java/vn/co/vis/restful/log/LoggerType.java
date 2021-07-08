package vn.co.vis.restful.log;

/**
 * @author Giang Thanh Quang
 * @since 11/19/2020
 */
public enum LoggerType {
    REQUEST("requestLog"),
    APPLICATION("applicationLog"),
    API("apiLog"),
    SQL("sqlLog");

    private String loggerName;

    LoggerType(String loggerName) {
        this.loggerName = loggerName;
    }

    public String getLoggerName() {
        return loggerName;
    }
}
