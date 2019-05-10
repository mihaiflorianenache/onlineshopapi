package org.fasttrackit;

import org.fasttrackit.Domain.Cart;
import org.fasttrackit.Domain.Customer;
import org.fasttrackit.Domain.Product;
import org.fasttrackit.Exception.ResourceNotFoundException;
import org.fasttrackit.Service.CartService;
import org.fasttrackit.Transfer.Customer.CustomerIdentifier;
import org.fasttrackit.Transfer.Product.ProductIdentifier;
import org.fasttrackit.Transfer.cart.SaveCartRequest;
import org.fasttrackit.steps.CustomerSteps;
import org.fasttrackit.steps.ProductSteps;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.CoreMatchers.is;



public class CartIntegrationTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductSteps productSteps;

    @Autowired
    private CustomerSteps customerSteps;

    @Test
    public void testAddProductsToCart_whenValidRequest_thenReturnCart() throws ResourceNotFoundException {
        Product product=productSteps.createProduct();
        Customer customer=customerSteps.createCustomer();

        SaveCartRequest request=new SaveCartRequest();
        request.setCustomerId(customer.getId());
        request.setProductIds(Collections.singleton(product.getId()));

        Cart cart=cartService.addProductsToCart(request);
        //todo:add assertions

        assertThat(cart,notNullValue());
        assertThat(cart.getId(),is(customer.getId()));

        assertThat(cart.getProducts(),notNullValue());
        assertThat(cart.getProducts(),hasSize(1));
    }
}
