package com.spring.ecom_backend.service;

import com.spring.ecom_backend.model.Product;
import com.spring.ecom_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository repo;
    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public Product addProducts(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageDate(imageFile.getBytes());
        return repo.save(product);
    }

    public List<Product> searchProducts(String keyward) {
        return repo.searchProducts(keyward);
    }

    public Product getProductById(int id) {
        return repo.findById(id).orElse(null);
    }

    public boolean deleteProduct(int id) {
        Optional<Product> product = repo.findById(id);
        repo.deleteById(id);
        return product.isPresent();
    }

    public boolean updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
        Optional<Product> optProduct = repo.findById(id);
        if (optProduct.isEmpty())
            return false;
        product.setReleaseDate(optProduct.get().getReleaseDate());
        addProducts(product, imageFile);
        return true;
    }
}
