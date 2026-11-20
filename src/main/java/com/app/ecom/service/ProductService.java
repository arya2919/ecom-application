package com.app.ecom.service;


import com.app.ecom.dto.ProductRequest;
import com.app.ecom.dto.ProductResponse;
import com.app.ecom.model.Product;
import com.app.ecom.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
//    public @Nullable List<ProductResponse> getAllProducts;

    public ProductResponse createProduct(ProductRequest productRequest) {
       Product product = new Product();
       updateProductfromRequest(product, productRequest);
       Product savedProduct = productRepository.save(product);
       return mapToProductResponse(savedProduct);

    }

    private ProductResponse mapToProductResponse(Product savedProduct) {

       ProductResponse productResponse = new ProductResponse();
        productResponse.setId(savedProduct.getId());
        productResponse.setName(savedProduct.getName());
        productResponse.setActive(savedProduct.isActive());
        productResponse.setDescription(savedProduct.getDescription());
        productResponse.setPrice(savedProduct.getPrice());
        productResponse.setCategory(savedProduct.getCategory());

        productResponse.setImageUrl(savedProduct.getImageUrl());
        productResponse.setStockQuantity(savedProduct.getStockQuantity());
        return productResponse;
    }

    private void updateProductfromRequest(Product product, ProductRequest productRequest) {

       product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
      //  product.setActive(productRequest.getActive());
        product.setPrice(productRequest.getPrice());
        product.setCategory(productRequest.getCategory());
        product.setImageUrl(productRequest.getImageUrl());
        product.setStockQuantity(productRequest.getStockQuantity());



    }

    public Optional <ProductResponse> updateProduct(Long id, ProductRequest productRequest) {
       return  productRepository.findById(id)
               .map(rxistingProduct -> {
                   updateProductfromRequest(rxistingProduct, productRequest);
                   Product savedProduct = productRepository.save(rxistingProduct);
                   return mapToProductResponse(savedProduct);
                   //   }).orElseThrow(() -> new RuntimeException("Product not found" + id));
               });
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findByActiveTrue().stream().map(this::mapToProductResponse).collect(Collectors.toList());    }

    public boolean deleteProduct(Long id) {
        return productRepository.findById(id)
                .map(product -> {
            product.setActive(false);
            productRepository.save(product);
            return true;
        }).orElse(false);

    }

    public List<ProductResponse> searchProducts(String keyword) {
       // return  productRepository.findByActiveTrue().stream().map(this::mapToProductResponse).collect(Collectors.toList());
        return  productRepository.searchProducts(keyword).stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }
}
