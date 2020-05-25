package com.assesment.vgginternship.orderstatus;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/api/orderstatus")
public class OrderStatusRestController {
    @Autowired
    private OrderStatusRepository orderStatusRepository;
    
    @GetMapping
    public Iterable<OrderStatusEntity> list() {
        return orderStatusRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<OrderStatusEntity> get(@PathVariable Long id) {
        Optional<OrderStatusEntity> orderStatusEntity = orderStatusRepository.findById(id);
        if (orderStatusEntity.isPresent()) {
            return new ResponseEntity<>(orderStatusEntity.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<OrderStatusEntity> put(@PathVariable Long id, @RequestBody OrderStatusEntity orderStatus) {
        Optional<OrderStatusEntity> orderStatusEntity = orderStatusRepository.findById(id);
        if (orderStatusEntity.isPresent()) {
            orderStatus.setId(orderStatusEntity.get().getId());
            return new ResponseEntity<>(orderStatusRepository.save(orderStatus), HttpStatus.OK);
        }
        return new ResponseEntity<>(orderStatusRepository.save(orderStatus), HttpStatus.CREATED);
    }
    
    @PatchMapping("/{id}")
    public OrderStatusEntity patch (@PathVariable Long id, @RequestBody OrderStatusEntity patch) {
        OrderStatusEntity orderStatusEntity = orderStatusRepository.findById(id).get();
        if (patch.getId() != null) {
            orderStatusEntity.setId(patch.getId());
        }
        if (patch.getName() != null) {
            orderStatusEntity.setName(patch.getName());
        }
        return orderStatusRepository.save(orderStatusEntity);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderStatusEntity post(@RequestBody OrderStatusEntity orderStatus) {
        return orderStatusRepository.save(orderStatus);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        try {
            orderStatusRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {}
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Error message")
    public void handleError() {
    }
    
}
