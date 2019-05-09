package org.fasttrackit.Persistence;

import org.fasttrackit.Domain.Cart;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart,Long> {
}
