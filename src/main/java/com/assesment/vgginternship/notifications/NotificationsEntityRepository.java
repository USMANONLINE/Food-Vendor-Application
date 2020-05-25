package com.assesment.vgginternship.notifications;

import com.assesment.vgginternship.customer.CustomerEntity;
import com.assesment.vgginternship.order.OrderEntity;
import com.assesment.vgginternship.vendor.VendorEntity;
import org.springframework.data.repository.CrudRepository;

public interface NotificationsEntityRepository extends CrudRepository<NotificationsEntity, Long> {
    Iterable<NotificationsEntity> findAllByVendor (VendorEntity vendor);
    Iterable<NotificationsEntity> findAllByCustomer (CustomerEntity customer);
    Iterable<NotificationsEntity> findAllByOrder (OrderEntity order);
}
