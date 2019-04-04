package org.fasttrackit;

import org.fasttrackit.Domain.Product;
import org.fasttrackit.Service.ProductService;
import org.fasttrackit.Transfer.CreateProductRequest;
import org.fasttrackit.Transfer.UpdateProductRequest;
import org.fasttrackit.onlineshopapi.exception.ResourceNotFoundException;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;


import static org.hamcrest.CoreMatchers.notNullValue;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@RunWith(SpringRunner.class)

@SpringBootTest
public class ProductServiceIntegrationTests {


    @Autowired
    private ProductService productService;


    @Test

    public void testCreateProduct_whenValidRequest_thenReturnProductWithId() {
        createProduct();
    }

    private Product createProduct() {
        CreateProductRequest request = new CreateProductRequest();

        request.setName("Laptop");
        request.setPrice(10);
        request.setQuantity(3);
        request.setSku("fdas321k231");


        Product product = productService.createProduct(request);

        assertThat(product, notNullValue());

        assertThat(product.getId(), greaterThan(0L));
        return product;
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetProduct_whenProductNotFound_thenThrowResourceNotFoundException() throws ResourceNotFoundException {
        productService.getProduct(0);
    }

    @Test
    public void testGetProduct_whenExistingId_thenReturnMatchingProduct() throws ResourceNotFoundException {
        Product product = createProduct();
        Product retrievedProduct = productService.getProduct(product.getId());

        assertThat(retrievedProduct.getId(), is(product.getName()));
        assertThat(retrievedProduct.getName(),is(product.getName()));
    }

    @Test
    public void testGetProduct_whenValidRequestWithAllFields_thenReturnUpdatedProduct() throws ResourceNotFoundException {
        Product createdProduct=createProduct();

        UpdateProductRequest request=new UpdateProductRequest();
        request.setName(createdProduct.getName()+"+Edited");
        request.setPrice(createdProduct.getPrice()+10);
        request.setQuantity(createdProduct.getQuantity()+10);
        request.setSku(createdProduct.getSku()+"_Edited");

        Product updatedProduct=productService.updateProduct(createdProduct.getId(),request);

        assertThat(updatedProduct.getName(),is(request.getName()));
        assertThat(updatedProduct.getName(),not(is(createdProduct.getName())));

        assertThat(updatedProduct.getPrice(),is(request.getPrice()));
        assertThat(updatedProduct.getQuantity(),is(request.getQuantity()));
        assertThat(updatedProduct.getSku(),is(request.getSku()));
    }

    //todo:Implement negativ test for update and tests for update with some fields only

    @Test
    public void testDeleteProduct_whenExistingId_thenProductIsDeleted() throws ResourceNotFoundException {
        Product createdProduct=createProduct();
        productService.deleteProduct(createdProduct.getId());
        productService.getProduct(createdProduct.getId());
    }
}

