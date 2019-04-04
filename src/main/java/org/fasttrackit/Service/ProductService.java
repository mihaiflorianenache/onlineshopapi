package org.fasttrackit.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.fasttrackit.Domain.Product;
import org.fasttrackit.Persistence.ProductRepository;
import org.fasttrackit.Transfer.CreateProductRequest;
import org.fasttrackit.Transfer.UpdateProductRequest;
import org.fasttrackit.onlineshopapi.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;



@Service
public class ProductService {
    private static final Logger LOGGER= LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;

    }

    public Product createProduct(CreateProductRequest request) {
        LOGGER.info("Creating product()",request);
        Product product = objectMapper.convertValue(request, Product.class);
        return productRepository.save(product);
    }

    public Product getProduct(long id) throws ResourceNotFoundException{
        LOGGER.info("Retrieving product()",id);
        return productRepository.findById(id)
                //optional and lambda expression
                .orElseThrow(()->new ResourceNotFoundException("Resource "+id+" not found"));
    }

    public Product updateProduct(long id, UpdateProductRequest request) throws ResourceNotFoundException {
        LOGGER.info("Updating product {} {}",id,request);
        Product product=getProduct(id);
        BeanUtils.copyProperties(request,product);
        return productRepository.save(product);
    }

    public void deleteProduct(long id){
        LOGGER.info("Deleting product {}",id);
        productRepository.deleteById(id);
        LOGGER.info("Deleted product {}",id);
    }
}