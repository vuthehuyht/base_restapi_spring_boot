package vn.co.vis.restful.log;

import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author Giang Thanh Quang
 * @since 11/19/2020
 */
public class LoggableDispatcherServlet extends DispatcherServlet {

    private static final AppLogger REQUEST_LOGGER = LoggerFactory.getLogger(LoggerType.REQUEST);

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request);
        }
        if (!(response instanceof ContentCachingResponseWrapper)) {
            response = new ContentCachingResponseWrapper(response);
        }
        HandlerExecutionChain handler = getHandler(request);
        try {
            super.doDispatch(request, response);
        } finally {
            log(request, response);
            updateResponse(response);
        }
    }

    private void log(HttpServletRequest requestToCache, HttpServletResponse responseToCache) {
        logRequest(requestToCache);
        logResponse(responseToCache);
    }

    private void logRequest(HttpServletRequest request) {
        StringBuilder msg = new StringBuilder();
        String url = request.getRequestURL().toString();
        msg.append(" Request ").append(url);
        String queryString = request.getQueryString();
        if (queryString != null) {
            msg.append(" Query=").append(request.getQueryString());
        }
        String payload = getRequestPayload(request);
        if (payload != null) {
            msg.append(" Payload=").append(payload);
        }

        // TODO: Required a log id for each request
        REQUEST_LOGGER.info(msg.toString(), "logID");
    }

    private void logResponse(HttpServletResponse response) {
        REQUEST_LOGGER.info(" Response=" + getResponsePayload(response));
    }

    private String getRequestPayload(HttpServletRequest request) {
        ContentCachingRequestWrapper wrapper =
                WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                try {
                    return new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                } catch (UnsupportedEncodingException ex) {
                    return "[unknown]";
                }
            }
        }
        return null;
    }

    private String getResponsePayload(HttpServletResponse response) {
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (wrapper != null) {

            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                try {
                    return new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                } catch (UnsupportedEncodingException ex) {
                }
            }
        }
        return "[unknown]";
    }

    private void updateResponse(HttpServletResponse response) throws IOException {
        ContentCachingResponseWrapper responseWrapper =
                WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        responseWrapper.copyBodyToResponse();
    }
}
