package vn.co.vis.restful.service;

import vn.co.vis.restful.dao.entity.Product;
import vn.co.vis.restful.dto.request.ProductRequest;
import vn.co.vis.restful.dto.response.StatusResponse;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<List<Product>> getAllProducts();
    Optional<Product> getProductById(int id);
    Optional<List<Product>> getProductByUsername(int id);
    Optional<StatusResponse> createProduct(ProductRequest product);
    Optional<StatusResponse> updateProduct(ProductRequest product, int id);
    Optional<StatusResponse> deleteProduct(int id);
}
