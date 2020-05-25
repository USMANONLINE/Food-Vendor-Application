package com.assesment.vgginternship.menu;

import com.assesment.vgginternship.vendor.VendorEntity;
import org.springframework.data.repository.CrudRepository;

public interface MenuRepository extends CrudRepository<MenuEntity, Long> {
    Iterable<MenuEntity> findAllByVendor (VendorEntity vendor);
}