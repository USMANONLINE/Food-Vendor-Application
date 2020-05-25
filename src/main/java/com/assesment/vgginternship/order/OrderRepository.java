package com.assesment.vgginternship.order;

import com.assesment.vgginternship.customer.CustomerEntity;
import com.assesment.vgginternship.vendor.VendorEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
    Iterable<OrderEntity> findByVendor (VendorEntity vendor);
    Iterable<OrderEntity> findByCustomer (CustomerEntity customer);
}
