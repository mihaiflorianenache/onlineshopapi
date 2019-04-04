package org.fasttrackit;

import org.fasttrackit.Domain.Product;
import org.fasttrackit.Service.ProductService;
import org.fasttrackit.Transfer.CreateProductRequest;
import org.fasttrackit.onlineshopapi.exception.ResourceNotFoundException;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;


import static org.hamcrest.CoreMatchers.notNullValue;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;


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
}

