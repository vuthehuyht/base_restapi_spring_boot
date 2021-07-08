package vn.co.vis.restful.service;

import vn.co.vis.restful.dao.entity.User;

import java.util.Optional;

/**
 * @author Giang Thanh Quang
 * @since 10/28/2020
 */
public interface VerifyService {

    String generateLoginToken(String userName);

    User verifyLoginToken(String token);
}
