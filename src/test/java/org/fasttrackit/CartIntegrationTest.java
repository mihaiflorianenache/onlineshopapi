package org.fasttrackit;

import org.fasttrackit.Domain.Customer;
import org.fasttrackit.Domain.Product;
import org.fasttrackit.Service.CartService;
import org.fasttrackit.Transfer.Customer.CustomerIdentifier;
import org.fasttrackit.Transfer.Product.ProductIdentifier;
import org.fasttrackit.Transfer.cart.SaveCartRequest;
import org.fasttrackit.steps.CustomerSteps;
import org.fasttrackit.steps.ProductSteps;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

public class CartIntegrationTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductSteps productSteps;

    @Autowired
    private CustomerSteps customerSteps;

    @Test
    public void testAddProductsToCart_whenValidRequest_thenReturnCart(){
        Product product=productSteps.createProduct();
        Customer customer=customerSteps.createCustomer();

        ProductIdentifier productIdentifier=new ProductIdentifier();
        productIdentifier.setId(product.getId());

        CustomerIdentifier customerIdentifier=new CustomerIdentifier();
        customerIdentifier.setId(customer.getId());

        SaveCartRequest request=new SaveCartRequest();
        request.setCustomer(customerIdentifier);
        request.setProducts(Collections.singleton(productIdentifier));

        cartService.addProductsToCart(request);
        //todo:add assertions
    }
}
