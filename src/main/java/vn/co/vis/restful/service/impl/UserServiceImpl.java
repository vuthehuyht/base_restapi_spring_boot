package vn.co.vis.restful.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.co.vis.restful.dao.entity.User;
import vn.co.vis.restful.dao.repository.UserRepository;
import vn.co.vis.restful.dto.response.UserResponse;
import vn.co.vis.restful.exception.ResourceNotFoundException;
import vn.co.vis.restful.service.AbstractService;
import vn.co.vis.restful.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Giang Thanh Quang
 * @since 10/28/2020
 */
@Service
public class UserServiceImpl extends AbstractService implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<UserResponse> getUser(String userName) {
        User user = userRepository.findById(userName).orElseThrow(() -> {
            throw new ResourceNotFoundException();
        });
        return Optional.of(new UserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone()));
    }

    @Override
    public Optional<List<UserResponse>> getUsers() {
        List<User> users = userRepository.getAllUsers().orElseThrow(() -> {
            throw new ResourceNotFoundException();
        });
        return Optional.of(users.stream()
                .map(user -> new UserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone()))
                .collect(Collectors.toList()));
    }
}
