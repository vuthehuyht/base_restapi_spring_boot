package vn.co.vis.restful.dao.repository;

import vn.co.vis.restful.dao.entity.Product;
import vn.co.vis.restful.dto.request.ProductRequest;
import vn.co.vis.restful.dto.response.StatusResponse;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Optional<List<Product>> getAllProducts();
    Optional<Product> getProduct(int id);
    Optional<List<Product>> getProductsByUserId(int id);
    Optional<StatusResponse> create(ProductRequest product);
    Optional<StatusResponse> update(int id, ProductRequest request);
    Optional<StatusResponse> deleteById(int id);
}
