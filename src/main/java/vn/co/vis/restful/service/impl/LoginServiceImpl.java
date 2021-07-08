package vn.co.vis.restful.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import vn.co.vis.restful.dao.entity.User;
import vn.co.vis.restful.dao.repository.UserRepository;
import vn.co.vis.restful.dto.request.LoginRequest;
import vn.co.vis.restful.dto.response.LoginResponse;
import vn.co.vis.restful.exception.RequestParamInvalidException;
import vn.co.vis.restful.exception.TokenInvalidException;
import vn.co.vis.restful.service.AbstractService;
import vn.co.vis.restful.service.LoginService;
import vn.co.vis.restful.service.VerifyService;

import java.util.Optional;


/**
 * @author Giang Thanh Quang
 * @since 10/15/2020
 */
@Service
public class LoginServiceImpl extends AbstractService implements LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerifyService verifyService;

    @Override
    public Optional<LoginResponse> login(LoginRequest request) {
        String message = validator.validateRequestThenReturnMessage(request);
        if (!StringUtils.isEmpty(message)) {
            throw new RequestParamInvalidException(message);
        }
        Optional<User> userData = userRepository.findByIdAndPassword(request.getUserName(), request.getPassword());
        return userData.map(user -> new LoginResponse(user.getId(), user.getFirstName(), user.getLastName(),
                verifyService.generateLoginToken(request.getUserName())));
    }
}
