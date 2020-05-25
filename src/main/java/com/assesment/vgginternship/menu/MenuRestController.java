package com.assesment.vgginternship.menu;

import com.assesment.vgginternship.vendor.VendorEntity;
import com.assesment.vgginternship.vendor.VendorRepository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/api/menu")
public class MenuRestController {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private VendorRepository vendorRepository;
    
    @GetMapping
    public Iterable<MenuEntity> list() {
        return menuRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public MenuEntity get(@PathVariable Long id) {
        return menuRepository.findById(id).get();
    }
    
    @GetMapping("/vendor/{vendorid}")
    public Iterable<MenuEntity> getAllByVendors (@PathVariable("vendorid") Long id) {
        Optional<VendorEntity> vendor = vendorRepository.findById(id);
        return menuRepository.findAllByVendor(vendor.get());
    }
    
    @PutMapping("/{id}")
    public MenuEntity put(@PathVariable Long id, @RequestBody MenuEntity menu) {
        menu.setId(id);
        return menuRepository.save(menu);
    }
    
    @PutMapping("/{id}/vendor/{vendorid}")
    public ResponseEntity<MenuEntity> putMenu(@PathVariable Long id, @PathVariable("vendorid") Long vendorid, @RequestBody MenuEntity menu) {
        Optional<VendorEntity> vendor = vendorRepository.findById(vendorid);
        if (vendor.isPresent()) {
            menu.setVendor(vendor.get());
            menu.setId(id);
            return new ResponseEntity<>(menuRepository.save(menu), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
    
    @PostMapping("/vendor/{vendorid}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MenuEntity> post(@PathVariable("vendorid") Long id, @RequestBody MenuEntity menu) {
        Optional<VendorEntity> vendor = vendorRepository.findById(id);
        if (vendor.isPresent()) {
            menu.setVendor(vendor.get());
            return new ResponseEntity<>(menuRepository.save(menu), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        try {
            menuRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {}
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Error message")
    public void handleError() {
    }
    
}
