package vn.co.vis.restful.dao.repository;

import vn.co.vis.restful.dao.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * @author Giang Thanh Quang
 * @since 10/16/2020
 */
public interface UserRepository {

    Optional<User> findById(String id);

    Optional<User> findByIdAndPassword(String id, String password);

    Optional<List<User>> getAllUsers();
}
