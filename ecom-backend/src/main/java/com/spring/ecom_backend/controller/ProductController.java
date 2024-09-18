package com.spring.ecom_backend.controller;

import com.spring.ecom_backend.model.Product;
import com.spring.ecom_backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping({"", "/"})
    List<Product> getProducts() {
        return service.getAllProducts();
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getProductById(@PathVariable int id) {
        Product product = service.getProductById(id);
        return product != null
                ? new ResponseEntity<>(product, HttpStatus.OK)
                : new ResponseEntity<>("Product Not Found", HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = {"", "/"}, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    ResponseEntity<?> addProducts(@RequestPart Product product, @RequestPart MultipartFile imageFile) {
        Product prod = null;
        try {
            prod = service.addProducts(product, imageFile);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return prod != null
                ? new ResponseEntity<>(prod, HttpStatus.ACCEPTED)
                : new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping("/search")
    ResponseEntity<?> searchProducts(@PathVariable String keyward) {
        List<Product> products = service.searchProducts(keyward);
        return products != null ? new ResponseEntity<>(products, HttpStatus.OK) : new ResponseEntity<>("No products available!", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/image")
    ResponseEntity<byte[]> getProductImage(@PathVariable int id) {
        Product product = service.getProductById(id);
        byte[] imageFile = product.getImageDate();
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImageType()))
                .body(imageFile);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteProduct(@PathVariable int id) {
        boolean isDeleted = service.deleteProduct(id);
        return isDeleted ? new ResponseEntity<>("Deleted Successfully", HttpStatus.OK) : new ResponseEntity<>("Product Not Found!", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateProduct(@PathVariable int id, @RequestPart Product product, @RequestPart MultipartFile imageFile) {
        try {
            boolean isUpdated = service.updateProduct(id, product, imageFile);
            return isUpdated ? new ResponseEntity<>("Updated Successfully", HttpStatus.ACCEPTED) : new ResponseEntity<>("Product Not Found!", HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
