package org.fasttrackit.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fasttrackit.Domain.Cart;
import org.fasttrackit.Domain.Customer;
import org.fasttrackit.Domain.Product;
import org.fasttrackit.Exception.ResourceNotFoundException;
import org.fasttrackit.Persistence.CartRepository;
import org.fasttrackit.Transfer.cart.SaveCartRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CartService {
    private static final Logger LOGGER= LoggerFactory.getLogger(CartService.class);
    private final CartRepository cartRepository;
    private final ObjectMapper objectMapper;
    private final CustomerService customerService;
    private final ProductService productService;

    
    @Autowired
    public CartService(CartRepository cartRepository, ObjectMapper objectMapper, CustomerService customerService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.objectMapper=objectMapper;
        this.customerService = customerService;
        this.productService = productService;
    }

    @Transactional
    public Cart addProductsToCart(SaveCartRequest request) throws ResourceNotFoundException {
        LOGGER.info("Adding products to cart: {}",request);
        Customer customer = customerService.getCustomer(request.getCustomerId());
        Cart cart = new Cart();
        cart.setCustomer(customer);
        for(long id:request.getProductIds()){
            //could be done more efficiently with a getAllProductByIds
            Product product=productService.getProduct(id);
            cart.addProduct(product);
        }
        return cartRepository.save(cart);
    }
}
