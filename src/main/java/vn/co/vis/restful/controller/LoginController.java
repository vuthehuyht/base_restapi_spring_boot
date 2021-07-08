package vn.co.vis.restful.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.co.vis.restful.dto.request.LoginRequest;
import vn.co.vis.restful.service.LoginService;

/**
 * @author Giang Thanh Quang
 * @since 10/15/2020
 */
@RestController
@RequestMapping("/login")
public class LoginController extends AbstractController<LoginService> {

    @PostMapping(value = "")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return response(service.login(request));
    }
}
