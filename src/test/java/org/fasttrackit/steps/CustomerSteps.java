package org.fasttrackit.steps;

import org.fasttrackit.Domain.Customer;
import org.fasttrackit.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerSteps {

    @Autowired
    private CustomerService customerService;

    public Customer createCustomer(){
        Customer customer=new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setAddress("test address");
        customer.setEmail("john@example.com");
        customer.setPhone("45678");
        customer.setUsername("johnny");
        customer.setPassword("pass");

        return customerService.createCustomer(customer);
    }
}
