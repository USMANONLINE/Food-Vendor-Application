package com.assesment.vgginternship.vendor;

import com.assesment.vgginternship.auth.AuthEntity;
import com.assesment.vgginternship.auth.AuthRepository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
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
@RequestMapping("/api/vendor")
public class VendorRestController {
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private AuthRepository authRepository;
    
    @GetMapping
    public Iterable<VendorEntity> list() {
        return vendorRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<VendorEntity> get(@PathVariable Long id) {
        Optional<VendorEntity> vendor = vendorRepository.findById(id);
        if (vendor.isPresent()) {
            return new ResponseEntity<>(vendor.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable Long id, @RequestBody VendorEntity vendor) {
        Optional<VendorEntity> vendorEntity = vendorRepository.findById(id);
        if (vendorEntity.isPresent()) {
            vendor.setId(vendorEntity.get().getId());
            vendor.setAuth(vendorEntity.get().getAuth());
            return new ResponseEntity<>(vendorRepository.save(vendor), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    
    @PostMapping("/auth/{authid}")
    public ResponseEntity<VendorEntity> post(@PathVariable Long authid, @RequestBody VendorEntity vendor) {
        Optional<AuthEntity> auth = authRepository.findById(authid);
        if (auth.isPresent()) {
            vendor.setAuth(auth.get());
            return new ResponseEntity<>(vendorRepository.save(vendor), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.PARTIAL_CONTENT);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        try {
            vendorRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {}
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Error message")
    public void handleError() {
    }
    
}
