package vn.co.vis.restful.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.co.vis.restful.service.UserService;

/**
 * @author Giang Thanh Quang
 * @since 10/28/2020
 */
@RestController
@RequestMapping("/users")
public class UserController extends AbstractController<UserService> {

    @GetMapping(value = "/{userName}")
    public ResponseEntity<?> getUser(@PathVariable String userName) {
        return response(service.getUser(userName));
    }

    @GetMapping(value = "")
    public ResponseEntity<?> getUsers() {
        return response(service.getUsers());
    }
}
