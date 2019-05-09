package org.fasttrackit.Transfer.cart;

import org.fasttrackit.Transfer.Customer.CustomerIdentifier;
import org.fasttrackit.Transfer.Product.ProductIdentifier;

import java.util.Set;

public class SaveCartRequest {

    private CustomerIdentifier customer;
    private Set<ProductIdentifier> products;

    public CustomerIdentifier getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerIdentifier customer) {
        this.customer = customer;
    }

    public Set<ProductIdentifier> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductIdentifier> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "SaveCartRequest{" +
                "customer=" + customer +
                ", products=" + products +
                '}';
    }
}
