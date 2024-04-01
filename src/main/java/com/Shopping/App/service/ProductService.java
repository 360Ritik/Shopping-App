package com.Shopping.App.service;

import com.Shopping.App.exceptionHandling.ResourceNotFoundException;
import com.Shopping.App.schema.Product;
import com.Shopping.App.service.repo.ProductRepository;
import org.springframework.stereotype.Service;


@Service
public class ProductService {


    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getInventory() {
        return productRepository.findById(1L).orElseThrow(() -> new ResourceNotFoundException("Product Not Found with Id: ", 1L));
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
}

