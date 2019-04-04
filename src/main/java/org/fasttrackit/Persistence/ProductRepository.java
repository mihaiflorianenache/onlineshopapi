package org.fasttrackit.Persistence;


import org.fasttrackit.Domain.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

}
