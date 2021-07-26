package vn.co.vis.restful.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.co.vis.restful.dao.entity.Product;
import vn.co.vis.restful.dao.repository.ProductRepository;
import vn.co.vis.restful.dto.request.ProductRequest;
import vn.co.vis.restful.dto.response.StatusResponse;
import vn.co.vis.restful.service.AbstractService;
import vn.co.vis.restful.service.ProductService;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl extends AbstractService implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Optional<List<Product>> getAllProducts() {
        return productRepository.getAllProducts();
    }

    @Override
    public Optional<Product> getProductById(int id) {
        return productRepository.getProduct(id);
    }

    @Override
    public Optional<List<Product>> getProductByUsername(int id) {
        return productRepository.getProductsByUserId(id);
    }

    @Override
    public Optional<StatusResponse> createProduct(ProductRequest product) {
        return productRepository.create(product);
    }

    @Override
    public Optional<StatusResponse> updateProduct(ProductRequest product, int id) {
        return productRepository.update(id, product);
    }

    @Override
    public Optional<StatusResponse> deleteProduct(int id) {
        return productRepository.deleteById(id);
    }
}
