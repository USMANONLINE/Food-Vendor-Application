package com.assesment.vgginternship.order;

import com.assesment.vgginternship.customer.CustomerEntity;
import com.assesment.vgginternship.customer.CustomerRepository;
import com.assesment.vgginternship.orderstatus.OrderStatusEntity;
import com.assesment.vgginternship.orderstatus.OrderStatusRepository;
import com.assesment.vgginternship.vendor.VendorEntity;
import com.assesment.vgginternship.vendor.VendorRepository;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/api/order")
public class OrderRestController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderStatusRepository orderStatusRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private VendorRepository vendorRepository;
    
    @GetMapping
    public Iterable<OrderEntity> list() {
        return orderRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public OrderEntity get(@PathVariable Long id) {
        return orderRepository.findById(id).get();
    }
    
    @GetMapping("/vendor/{vendorid}")
    public Iterable<OrderEntity> getAllByVendor (@PathVariable("vendorid") Long id) {
        Optional<VendorEntity> vendor = vendorRepository.findById(id);
        return orderRepository.findByVendor(vendor.get());
    }
    
    @GetMapping("/customer/{customerid}")
    public Iterable<OrderEntity> getAllByCustmer (@PathVariable("customerid") Long id) {
        Optional<CustomerEntity> customer = customerRepository.findById(id);
        return orderRepository.findByCustomer(customer.get());
    }
    
    @PutMapping("/complete/{orderid}/customer/{customerid}/vendor/{vendorid}/")
    public ResponseEntity<OrderEntity> completeTransaction (@PathVariable("customerid") Long customerid,
                                                            @PathVariable("vendorid") Long vendorid,
                                                            @PathVariable("orderid") Long orderid,
                                                            @PathVariable("orderstatusid") Long orderstatusid,
                                                            @RequestBody OrderEntity order) {
        Optional<CustomerEntity> customer = customerRepository.findById(customerid);
        Optional<VendorEntity> vendor = vendorRepository.findById(vendorid);
        Optional<OrderEntity> orderntity = orderRepository.findById(orderid);
        Optional<OrderStatusEntity> orderStatus = orderStatusRepository.findByName("COMPLETE");
        if (customer.isPresent() && vendor.isPresent() && orderntity.isPresent() && orderStatus.isPresent()) {
            order.setId(orderntity.get().getId());
            order.setCustomer(customer.get());
            order.setVendor(vendor.get());
            order.setOrderStatus(orderStatus.get());
            return new ResponseEntity<>(orderRepository.save(order), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.PARTIAL_CONTENT);
    }
    
    
    @PutMapping("/cancel/{orderid}/customer/{customerid}/vendor/{vendorid}/")
    public ResponseEntity<OrderEntity> cancelTransaction (@PathVariable("customerid") Long customerid,
                                                            @PathVariable("vendorid") Long vendorid,
                                                            @PathVariable("orderid") Long orderid,
                                                            @PathVariable("orderstatusid") Long orderstatusid,
                                                            @RequestBody OrderEntity order) {
        Optional<CustomerEntity> customer = customerRepository.findById(customerid);
        Optional<VendorEntity> vendor = vendorRepository.findById(vendorid);
        Optional<OrderEntity> orderntity = orderRepository.findById(orderid);
        Optional<OrderStatusEntity> orderStatus = orderStatusRepository.findByName("CANCEL");
        if (customer.isPresent() && vendor.isPresent() && orderntity.isPresent() && orderStatus.isPresent()) {
            order.setId(orderntity.get().getId());
            order.setCustomer(customer.get());
            order.setVendor(vendor.get());
            order.setOrderStatus(orderStatus.get());
            return new ResponseEntity<>(orderRepository.save(order), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.PARTIAL_CONTENT);
    }
    
    @PutMapping("/{orderid}/customer/{customerid}/vendor/{vendorid}/orderstatus/{orderstatusid}")
    public ResponseEntity<OrderEntity> put(@PathVariable("customerid") Long customerid,
                                            @PathVariable("vendorid") Long vendorid,
                                            @PathVariable("orderid") Long orderid,
                                            @PathVariable("orderstatusid") Long orderstatusid,
                                            @RequestBody OrderEntity order) {
        Optional<CustomerEntity> customer = customerRepository.findById(customerid);
        Optional<VendorEntity> vendor = vendorRepository.findById(vendorid);
        Optional<OrderEntity> orderntity = orderRepository.findById(orderid);
        Optional<OrderStatusEntity> orderStatus = orderStatusRepository.findById(orderstatusid);
        if (customer.isPresent() && vendor.isPresent() && orderntity.isPresent() && orderStatus.isPresent()) {
            order.setId(orderntity.get().getId());
            order.setCustomer(customer.get());
            order.setVendor(vendor.get());
            order.setOrderStatus(orderStatus.get());
            return new ResponseEntity<>(orderRepository.save(order), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.PARTIAL_CONTENT);
    }
    
    @PostMapping("/customer/{customerid}/vendor/{vendorid}")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderEntity post(@PathVariable("customerid") Long customerid,
                            @PathVariable("vendorid") Long vendorid,
                            @RequestBody OrderEntity order) {
        Optional<OrderStatusEntity> orderStatus = orderStatusRepository.findByName("ACTIVE");
        Optional<VendorEntity> vendor = vendorRepository.findById(vendorid);
        Optional<CustomerEntity> customer = customerRepository.findById(customerid);
        if (vendor.isPresent() && customer.isPresent() && orderStatus.isPresent()) {
            order.setVendor(vendor.get());
            order.setCustomer(customer.get());
            order.setOrderStatus(orderStatus.get());
        }
        return orderRepository.save(order);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        try {
            orderRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {}
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Error message")
    public void handleError() {
    }
    
}
