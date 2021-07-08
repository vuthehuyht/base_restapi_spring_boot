package vn.co.vis.restful.exchange;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import vn.co.vis.restful.log.AppLogger;
import vn.co.vis.restful.log.LoggerFactory;
import vn.co.vis.restful.log.LoggerType;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

/**
 * ApiExchangeService using to call from the system to other system
 * Using Restful API or Form data
 */
@Component
public class ApiExchangeService {


    /**
     * the create restTemplate
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * the create mapper
     */
    private ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static final AppLogger API_LOGGER = LoggerFactory.getLogger(LoggerType.API);


    /**
     * Create url from domain and paths
     *
     * @param domain domain
     * @param paths  paths
     * @return url
     */
    public String createURL(String domain, String... paths) {
        StringBuilder builder = new StringBuilder(domain);
        for (String path : paths) {
            builder.append("/" + path);
        }
        return builder.toString();
    }


    /**
     * Transfer HttpHeaders to request
     *
     * @param httpRequest HttpServletRequest
     * @return HttpHeaders transfer
     */
    private HttpHeaders transfer(HttpServletRequest httpRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", httpRequest.getHeader("User-Agent"));
        headers.set("X-Forwarded-For", httpRequest.getRemoteAddr());
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }

    /**
     * Create HttpEntity of a HttpServletRequest
     *
     * @param httpRequest HttpServletRequest
     * @return HttpEntity with transfer request
     */
    private HttpEntity<Object> createEntity(HttpServletRequest httpRequest) {
        return new HttpEntity<>(transfer(httpRequest));
    }

    /**
     * Create entity from HttpServletRequest
     *
     * @param httpRequest HttpServletRequest
     * @param data        Object
     * @return HttpEntity after transfer httpRequest with data
     */
    private HttpEntity<Object> createEntity(HttpServletRequest httpRequest, Object data) {
        HttpHeaders headers = transfer(httpRequest);
        return new HttpEntity<>(data, headers);
    }

    /**
     * Create HttpEntity
     *
     * @param data data
     * @return HttpEntity with data
     */
    private HttpEntity<Object> createEntity(Object data) {
        HttpHeaders headers = new HttpHeaders();
        return new HttpEntity<>(data, headers);
    }

    /**
     * Get object data by GET to other system
     *
     * @param httpRequest HttpServletRequest
     * @param url         url to get
     * @param classType   class type
     * @param <T>         Type
     * @return object data with type T
     */
    public <T> T get(HttpServletRequest httpRequest, String url, Class<T> classType) {
        HttpEntity<?> entity = createEntity(httpRequest);
        try {
            ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, entity, classType);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            // handle http client exception here
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Get object data by POST to other system
     *
     * @param httpRequest HttpServletRequest
     * @param url         String
     * @param request     Object
     * @param classType   classType T
     * @param <T>         type T
     * @return data object with type T
     */
    public <T> T post(HttpServletRequest httpRequest, String url, Object request, Class<T> classType) {
        HttpEntity<Object> requestEntity = createEntity(httpRequest, request);
        try {
            ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, classType);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * create post method for call API with application/x-www-form-urlencoded
     *
     * @param httpRequest HttpServletRequest
     * @param url         String
     * @param requestBody param in form
     * @param classType   Type
     * @param <T>         type T
     * @return Response entity with type T
     */
    public <T> ResponseEntity<T> post(HttpServletRequest httpRequest, String url, MultiValueMap<String, String> requestBody, Class<T> classType) {
        HttpHeaders headers = createHeader(httpRequest, MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            T res = mapper.readValue(response.getBody(), classType);
            return new ResponseEntity<>(res, response.getStatusCode());
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Get object data by PUT to other system
     *
     * @param httpRequest HttpServletRequest
     * @param url         url to PUT
     * @param request     Object data as json
     * @param classType   classType T
     * @param <T>         type T
     * @return data reponse as typt T
     */
    public <T> ResponseEntity<T> put(HttpServletRequest httpRequest, String url,
                                     Object request, Class<T> classType) {
        HttpEntity<Object> requestEntity = createEntity(httpRequest, request);
        try {
            ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.PUT,
                    requestEntity, classType);
            return response;
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Get object data by DELETE to other system
     *
     * @param httpRequest request
     * @param url         url to exchange
     * @param request     request object
     * @param classType   class type
     * @param <T>         type T
     * @return ResponseEntity object as type T
     */
    private <T> ResponseEntity<T> delete(HttpServletRequest httpRequest, String url, Object request, Class<T> classType) {
        HttpEntity<Object> requestEntity = createEntity(httpRequest, request);
        try {
            ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, classType);
            return response;
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Get cookie value from HttpServletRequest
     *
     * @param req        HttpServletRequest
     * @param cookieName cookie to get
     * @return value of cookie
     */
    private String getCookieValue(HttpServletRequest req, String cookieName) {
        Cookie[] cookies = req == null ? null : req.getCookies();
        if (cookies == null || cookies.length == 0) {
            return null;
        }

        return Arrays.stream(cookies).filter(c -> c.getName().equals(cookieName)).findFirst()
                .map(Cookie::getValue).orElse(null);
    }

    /**
     * Create header with media type
     *
     * @param httpRequest request
     * @param mediaType   Media type
     * @return HttpHeaders
     */
    private HttpHeaders createHeader(HttpServletRequest httpRequest, MediaType mediaType) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", httpRequest.getHeader("User-Agent"));
        headers.set("X-Forwarded-For", httpRequest.getRemoteAddr());
        headers.setContentType(mediaType);

        return headers;
    }


}
