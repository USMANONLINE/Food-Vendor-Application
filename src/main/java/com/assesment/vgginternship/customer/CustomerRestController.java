package com.assesment.vgginternship.customer;

import com.assesment.vgginternship.auth.AuthEntity;
import com.assesment.vgginternship.auth.AuthRepository;
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
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/api/customer")
public class CustomerRestController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AuthRepository authRepository;
    
    @GetMapping()
    public Iterable<CustomerEntity> list() {
        return customerRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Object get(@PathVariable Long id) {
        Optional<CustomerEntity> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            return new ResponseEntity<>(customer.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CustomerEntity> put(@PathVariable Long id, @RequestBody CustomerEntity customer) {
        Optional<CustomerEntity> customerEntity = customerRepository.findById(id);
        if (customerEntity.isPresent()) {
            customer.setId(customerEntity.get().getId());
            customer.setAuth(customerEntity.get().getAuth());
            return new ResponseEntity<>(customerRepository.save(customer), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    
    @PostMapping("/auth/{authid}")
    public ResponseEntity<CustomerEntity> post(@PathVariable("authid") Long id, @RequestBody CustomerEntity customer) {
        Optional<AuthEntity> auth = authRepository.findById(id);
        if (auth.isPresent()) {
            customer.setAuth(auth.get());
            return new ResponseEntity<>(customerRepository.save(customer), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.PARTIAL_CONTENT);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        try {
            customerRepository.findById(id);
        } catch (EmptyResultDataAccessException e) {
        }
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Error message")
    public void handleError() {
    }
    
}
