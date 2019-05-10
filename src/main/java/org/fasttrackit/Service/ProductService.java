package org.fasttrackit.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fasttrackit.Domain.Product;
import org.fasttrackit.Exception.ResourceNotFoundException;
import org.fasttrackit.Persistence.ProductRepository;
import org.fasttrackit.Transfer.Product.CreateProductRequest;
import org.fasttrackit.Transfer.Product.GetProductsRequest;
import org.fasttrackit.Transfer.Product.UpdateProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
    }

    public Product createProduct(CreateProductRequest request) {
        LOGGER.info("Creating product {}", request);
        Product product = objectMapper.convertValue(request, Product.class);
        return productRepository.save(product);
    }

    public Product getProduct(long id) throws ResourceNotFoundException {
        LOGGER.info("Retrieving product {}", id);
        return productRepository.findById(id)
                // Optional and lambda expression
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product " + id + " not found"));
    }

    public Page<Product> getProducts(GetProductsRequest request, Pageable pageable) {
        LOGGER.info("Retrieving products >> {}", request);

        // not an elegant solution, but the easiest one for now
        if (request.getPartialName() != null &&
                request.getMinimumQuantity() != null &&
                request.getMinimumPrice() != null &&
                request.getMaximumPrice() != null) {

            return productRepository.findByNameContainingAndPriceBetweenAndQuantityGreaterThanEqual(
                    request.getPartialName(), request.getMinimumPrice(),
                    request.getMaximumPrice(), request.getMinimumQuantity(), pageable);

        } else if (request.getMinimumPrice() != null &&
                request.getMaximumPrice() != null &&
                request.getMinimumQuantity() != null) {
            return productRepository.findByPriceBetweenAndQuantityGreaterThanEqual(
                    request.getMinimumPrice(), request.getMaximumPrice(),
                    request.getMinimumQuantity(), pageable);

        } else if (request.getPartialName() != null &&
                request.getMinimumQuantity() != null) {
            return productRepository.findByNameContainingAndQuantityGreaterThanEqual(
                    request.getPartialName(), request.getMinimumQuantity(), pageable);
        }

        return productRepository.findAll(pageable);
    }

    public Product updateProduct(long id, UpdateProductRequest request) throws ResourceNotFoundException {
        LOGGER.info("Updating product {}, {}", id, request);
        Product product = getProduct(id);
        BeanUtils.copyProperties(request, product);
        return productRepository.save(product);
    }

    public void deleteProduct(long id) {
        LOGGER.info("Deleting product {}", id);
        productRepository.deleteById(id);
        LOGGER.info("Deleted product {}", id);
    }


}