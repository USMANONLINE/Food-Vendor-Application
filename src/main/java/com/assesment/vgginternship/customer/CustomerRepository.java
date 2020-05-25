package com.assesment.vgginternship.customer;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {
    
}
