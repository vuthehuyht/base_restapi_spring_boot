package vn.co.vis.restful.dao.repository.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import vn.co.vis.restful.dao.entity.User;
import vn.co.vis.restful.dao.repository.AbstractRepository;
import vn.co.vis.restful.dao.repository.UserRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Giang Thanh Quang
 * @since 10/16/2020
 */
@Repository
public class UserRepositoryImpl extends AbstractRepository implements UserRepository {

    @Override
    public Optional<User> findById(String id) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ").append(attributeNamesForSelect(User.class));
        sql.append(" FROM ").append(getSimpleNameTable(User.class));
        sql.append(" WHERE id = ?");
        User user = jdbcTemplate.queryForObject(sql.toString(), new String[]{id}, new BeanPropertyRowMapper<>(User.class));
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByIdAndPassword(String id, String password) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ").append(attributeNamesForSelect(User.class));
        sql.append(" FROM ").append(getSimpleNameTable(User.class));
        sql.append(" WHERE id = ? and password = ?");
        User user = jdbcTemplate.queryForObject(sql.toString(), new String[]{id, password}, new BeanPropertyRowMapper<>(User.class));
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<List<User>> getAllUsers() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ").append(attributeNamesForSelect(User.class));
        sql.append(" FROM ").append(getSimpleNameTable(User.class));
        List<User> users = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(User.class));
        return Optional.ofNullable(users);
    }
}
