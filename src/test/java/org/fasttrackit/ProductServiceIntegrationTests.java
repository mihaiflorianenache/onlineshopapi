package org.fasttrackit;

import org.fasttrackit.Domain.Product;
import org.fasttrackit.Exception.ResourceNotFoundException;
import org.fasttrackit.Service.CartService;
import org.fasttrackit.Service.ProductService;
import org.fasttrackit.Transfer.Product.CreateProductRequest;
import org.fasttrackit.Transfer.Product.GetProductsRequest;
import org.fasttrackit.Transfer.Product.UpdateProductRequest;

import org.fasttrackit.steps.CustomerSteps;
import org.fasttrackit.steps.ProductSteps;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;

import org.springframework.test.context.junit4.SpringRunner;



import static org.hamcrest.CoreMatchers.notNullValue;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;



@RunWith(SpringRunner.class)

@SpringBootTest

public class ProductServiceIntegrationTests {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductSteps productSteps;

    @Autowired
    private CustomerSteps customerSteps;


    @Test

    public void testCreateProduct_whenValidRequest_thenReturnProductWithId() {

        Product product = createProduct();



        assertThat(product, notNullValue());

        assertThat(product.getId(), greaterThan(0L));

    }



    private Product createProduct() {

        CreateProductRequest request = new CreateProductRequest();

        request.setName("Laptop");

        request.setPrice(10);

        request.setQuantity(3);

        request.setSku("fdas321k231");



        return productSteps.createProduct();

    }



    @Test(expected = ResourceNotFoundException.class)

    public void testGetProduct_whenProductNotFound_thenThrowResourceNotFoundException() throws ResourceNotFoundException {

        productService.getProduct(0);

    }



    @Test

    public void testGetProduct_whenExistingId_thenReturnMatchingProduct() throws ResourceNotFoundException {

        Product product = createProduct();



        Product retrievedProduct = productService.getProduct(product.getId());



        assertThat(retrievedProduct.getId(), is(product.getId()));

        assertThat(retrievedProduct.getName(), is(product.getName()));

    }



    @Test

    public void testUpdateProduct_whenValidRequestWithAllFields_thenReturnUpdatedProduct() throws ResourceNotFoundException {

        Product createdProduct = createProduct();



        UpdateProductRequest request = new UpdateProductRequest();

        request.setName(createdProduct.getName() + "_Edited");

        request.setPrice(createdProduct.getPrice() + 10);

        request.setQuantity(createdProduct.getQuantity() + 10);

        request.setSku(createdProduct.getSku() + "_Edited");



        Product updatedProduct =

                productService.updateProduct(createdProduct.getId(), request);



        assertThat(updatedProduct.getName(), is(request.getName()));

        assertThat(updatedProduct.getName(), not(is(createdProduct.getName())));



        assertThat(updatedProduct.getPrice(), is(request.getPrice()));

        assertThat(updatedProduct.getQuantity(), is(request.getQuantity()));

        assertThat(updatedProduct.getSku(), is(request.getSku()));



        assertThat(updatedProduct.getId(), is(createdProduct.getId()));

    }



    // todo: Implement negative tests for update and tests for update with some fields only



    @Test(expected = ResourceNotFoundException.class)

    public void testDeleteProduct_whenExistingId_thenProductIsDeleted() throws ResourceNotFoundException {

        Product createdProduct = createProduct();



        productService.deleteProduct(createdProduct.getId());



        productService.getProduct(createdProduct.getId());

    }



    @Test

    public void testGetProducts_whenAllCriteriaProvidedAndMatching_thenReturnFilteredResults() {

        Product createdProduct = createProduct();



        GetProductsRequest request = new GetProductsRequest();

        request.setPartialName("top");

        request.setMinimumPrice(9.9);

        request.setMaximumPrice(10.1);

        request.setMinimumQuantity(1);



        Page<Product> products =

                productService.getProducts(request, PageRequest.of(0, 10));



        assertThat(products.getTotalElements(), greaterThanOrEqualTo(1L));



        // todo: for each product from the response assert that all criteria are matched



    }



}