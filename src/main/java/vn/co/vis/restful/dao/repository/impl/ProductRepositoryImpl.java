package vn.co.vis.restful.dao.repository.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import vn.co.vis.restful.dao.entity.Product;
import vn.co.vis.restful.dao.repository.AbstractRepository;
import vn.co.vis.restful.dao.repository.ProductRepository;
import vn.co.vis.restful.dto.request.ProductRequest;
import vn.co.vis.restful.dto.response.StatusResponse;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryImpl extends AbstractRepository implements ProductRepository {
    @Override
    public Optional<List<Product>> getAllProducts() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ").append(attributeNamesForSelect(Product.class));
        sql.append(" FROM ").append(getSimpleNameTable(Product.class));
        List<Product> products = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(Product.class));
        return Optional.of(products);
    }

    @Override
    public Optional<Product> getProduct(int id) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ").append(attributeNamesForSelect(Product.class));
        sql.append(" FROM ").append(getSimpleNameTable(Product.class));
        sql.append(" WHERE id = ?");
        Product product = jdbcTemplate.queryForObject(sql.toString(), new Integer[]{id}, new BeanPropertyRowMapper<>(Product.class));
        return Optional.ofNullable(product);
    }

    @Override
    public Optional<List<Product>> getProductsByUserId(int id) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ").append(attributeNamesForSelect(Product.class));
        sql.append(" FROM ").append(getSimpleNameTable(Product.class));
        sql.append(" WHERE username = ?");
        List<Product> products = jdbcTemplate.query(sql.toString(), new Integer[]{id}, new BeanPropertyRowMapper<>(Product.class));
        return Optional.of(products);
    }

    @Override
    public Optional<StatusResponse> create(ProductRequest product) {
        String sql = "insert into product(name,quantity, username) values (?,?,?)";
        int status = jdbcTemplate.update(sql, product.getName(), product.getQuantity(), product.getUsername());
        if (status == 1) {
            return Optional.of(new StatusResponse("SUCCESS", "Created"));
        }
        return Optional.of(new StatusResponse("FAILURE", "Do not be created"));
    }

    @Override
    public Optional<StatusResponse> update(int id, ProductRequest request) {
        String sql = "update " + getSimpleNameTable(Product.class) + " set name=?, quantity=?, username=? where id=?";
        int status = jdbcTemplate.update(sql, request.getName(), request.getQuantity(), request.getUsername(), id);
        if (status == 1) {
            return Optional.of(new StatusResponse("SUCCESS", "Updated"));
        }
        return Optional.of(new StatusResponse("FAILURE", "Do not be update"));
    }

    @Override
    public Optional<StatusResponse> deleteById(int id) {
        String sql = "delete from " + getSimpleNameTable(Product.class) + " where id=?";
        int status = jdbcTemplate.update(sql, id);
        if (status == 1) {
            return Optional.of(new StatusResponse("SUCCESS", "Deleted"));
        }
        return Optional.of(new StatusResponse("FAILURE", "Do not be deleted"));
    }
}
