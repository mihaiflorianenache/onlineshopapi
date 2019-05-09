package org.fasttrackit.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fasttrackit.Domain.Customer;
import org.fasttrackit.Exception.ResourceNotFoundException;
import org.fasttrackit.Persistence.CustomerRepository;
import org.fasttrackit.Transfer.Customer.CreateCustomerRequest;
import org.fasttrackit.Transfer.Customer.UpdateCustomerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository customerRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, ObjectMapper objectMapper) {
        this.customerRepository = customerRepository;
        this.objectMapper = objectMapper;
    }

    public Customer createCustomer(CreateCustomerRequest request) {
        LOGGER.info("Creating customer {}", request);
        Customer customer = objectMapper.convertValue(request, Customer.class);
        return customerRepository.save(customer);
    }

    public Customer getCustomer(long id) throws ResourceNotFoundException {
        LOGGER.info("Retrieving customer {}", id);
        return customerRepository.findById(id)
                // Optional and lambda expression
                .orElseThrow(() -> new ResourceNotFoundException(
                       "Customer " + id + " not found"));
    }

    public Customer updateCustomer(long id, UpdateCustomerRequest request) throws ResourceNotFoundException {
        LOGGER.info("Updating customer {}, {}", id, request);
        Customer customer = getCustomer(id);
        BeanUtils.copyProperties(request, customer);
        return customerRepository.save(customer);
    }

    public void deleteCustomer(long id) {
        LOGGER.info("Deleting customer {}", id);
        customerRepository.deleteById(id);
        LOGGER.info("Deleted customer {}", id);
    }
}