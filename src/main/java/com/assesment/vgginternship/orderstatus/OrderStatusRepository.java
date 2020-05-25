package com.assesment.vgginternship.orderstatus;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
public interface OrderStatusRepository extends CrudRepository<OrderStatusEntity, Long> {
    Optional<OrderStatusEntity> findByName (String name);
}
