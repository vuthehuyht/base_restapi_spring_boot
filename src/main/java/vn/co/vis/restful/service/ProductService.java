package vn.co.vis.restful.service;

import vn.co.vis.restful.dao.entity.Product;
import vn.co.vis.restful.dto.request.NewProductRequest;
import vn.co.vis.restful.dto.response.StatusResponse;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<List<Product>> getAllProducts();
    Optional<Product> getProductById(int id);
    Optional<List<Product>> getProductByUsername(String username);
    Optional<StatusResponse> createProduct(NewProductRequest product);
    Optional<StatusResponse> updateProduct(NewProductRequest product, int id);
    Optional<StatusResponse> deleteProduct(int id);
}
