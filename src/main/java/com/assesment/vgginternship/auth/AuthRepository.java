package com.assesment.vgginternship.auth;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface AuthRepository extends CrudRepository<AuthEntity, Long> {
    Optional<AuthEntity> findByUsername (String username);
}
