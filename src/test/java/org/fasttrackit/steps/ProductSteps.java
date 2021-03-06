package org.fasttrackit.steps;

import org.fasttrackit.Domain.Product;
import org.fasttrackit.Service.ProductService;
import org.fasttrackit.Transfer.Product.CreateProductRequest;
import org.springframework.stereotype.Component;

@Component
public class ProductSteps {

    private ProductService productService;

    public Product createProduct(){
        CreateProductRequest request=new CreateProductRequest();
        request.setName("Laptop");
        request.setPrice(10);
        request.setQuantity(3);
        request.setSku("fdas321k231");
        return productService.createProduct(request);
    }
}
