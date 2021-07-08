package vn.co.vis.restful.service;

import vn.co.vis.restful.dto.request.LoginRequest;
import vn.co.vis.restful.dto.response.LoginResponse;

import java.util.Optional;

/**
 * @author Giang Thanh Quang
 * @since 10/15/2020
 */
public interface LoginService {
    Optional<LoginResponse> login(LoginRequest request);
}
