package vn.co.vis.restful.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.co.vis.restful.dto.request.ProductRequest;
import vn.co.vis.restful.service.ProductService;

import javax.validation.Valid;

@RestController
public class ProductController extends AbstractController<ProductService> {
    @GetMapping(value = "/products")
    public ResponseEntity<?> getAllProducts() {
        return response(service.getAllProducts());
    }

    @GetMapping(value = "/products/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable int productId) {
        return response(service.getProductById(productId));
    }

    @GetMapping(value = "/{username}/products")
    public ResponseEntity<?> getProductByUsername(@PathVariable String username) {
        return response(service.getProductByUsername(username));
    }

    @PostMapping(value = "/products/add")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductRequest request) {
        return response(service.createProduct(request));
    }

    @PutMapping(value = "/products/{productId}")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductRequest request, @PathVariable int productId) {
        return response(service.updateProduct(request, productId));
    }

    @DeleteMapping(value = "/products/{productId}")
    public ResponseEntity<?> createProduct(@PathVariable int productId) {
        return response(service.deleteProduct(productId));
    }
}
