package com.assesment.vgginternship.notifications;

import com.assesment.vgginternship.customer.CustomerEntity;
import com.assesment.vgginternship.customer.CustomerRepository;
import com.assesment.vgginternship.messagestatus.MessageStatusEntity;
import com.assesment.vgginternship.messagestatus.MessageStatusEntityRepository;
import com.assesment.vgginternship.order.OrderEntity;
import com.assesment.vgginternship.order.OrderRepository;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/api/notifications")
public class NotificationsRestController {
    @Autowired
    private NotificationsEntityRepository notificationsRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MessageStatusEntityRepository messageStatusRepository;
    
    @GetMapping()
    public Iterable<NotificationsEntity> list() {
        return notificationsRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<NotificationsEntity> get(@PathVariable Long id) {
        Optional<NotificationsEntity> notification = notificationsRepository.findById(id);
        if (notification.isPresent()) {
            return new ResponseEntity<>(notification.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/customer/{customerid}")
    public Iterable<NotificationsEntity> listAllNotificationByCustomer(@PathVariable("customerid") Long id) {
        Optional<CustomerEntity> customer = customerRepository.findById(id);
        return notificationsRepository.findAllByCustomer(customer.get());
    }
    
    @GetMapping("/vendor/{vendorid}")
    public Iterable<NotificationsEntity> listAllNotificationByVendor (@PathVariable("vendorid") Long id) {
        Optional<VendorEntity> vendor = vendorRepository.findById(id);
        return notificationsRepository.findAllByVendor(vendor.get());
    }
    
    @GetMapping("/order/{orderid}")
    public Iterable<NotificationsEntity> listAllNotificationByOrder (@PathVariable("orderid") Long id) {
        Optional<OrderEntity> order = orderRepository.findById(id);
        return notificationsRepository.findAllByOrder(order.get());
    }
    
    @PutMapping("/customer/{customerid/order/{orderid}/messagestatus/{messagestatusid}")
    public ResponseEntity<NotificationsEntity> put(@RequestBody NotificationsEntity notification, 
                                    @PathVariable("customerid") Long customerid,
                                    @PathVariable("orderid") Long orderid,
                                    @PathVariable("messagestatusid") Long messagestatusid) {
        Optional<CustomerEntity> customer = customerRepository.findById(customerid);
        Optional<OrderEntity> order = orderRepository.findById(orderid);
        Optional<MessageStatusEntity> message = messageStatusRepository.findById(messagestatusid);
        
        if (customer.isPresent() && order.isPresent() && message.isPresent()) {
            notification.setCustomer(customer.get());
            notification.setMessageStatus(message.get());
            notification.setOrder(order.get());
            return new ResponseEntity<>(notificationsRepository.save(notification), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
    
    @PutMapping("/vendor/{vendorid}/order/{orderid}/messagestatus/{messagestatusid}")
    public ResponseEntity<NotificationsEntity> vendorPut(@RequestBody NotificationsEntity notification, 
                                    @PathVariable("vendorid") Long vendorid,
                                    @PathVariable("orderid") Long orderid,
                                    @PathVariable("messagestatusid") Long messagestatusid) {
        Optional<VendorEntity> vendor = vendorRepository.findById(vendorid);
        Optional<OrderEntity> order = orderRepository.findById(orderid);
        Optional<MessageStatusEntity> message = messageStatusRepository.findById(messagestatusid);
        
        if (vendor.isPresent() && order.isPresent() && message.isPresent()) {
            notification.setVendor(vendor.get());
            notification.setMessageStatus(message.get());
            notification.setOrder(order.get());
            return new ResponseEntity<>(notificationsRepository.save(notification), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
    
    // Customer Notificaion
    @PostMapping("/customer/{customerid}/order/{orderid}/messagestatus/{messagestatusid}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<NotificationsEntity> customerPost(@RequestBody NotificationsEntity notification, 
                                    @PathVariable("customerid") Long customerid,
                                    @PathVariable("orderid") Long orderid,
                                    @PathVariable("messagestatusid") Long messagestatusid) {
        Optional<CustomerEntity> customer = customerRepository.findById(customerid);
        Optional<OrderEntity> order = orderRepository.findById(orderid);
        Optional<MessageStatusEntity> message = messageStatusRepository.findById(messagestatusid);
        
        if (customer.isPresent() && order.isPresent() && message.isPresent()) {
            notification.setCustomer(customer.get());
            notification.setMessageStatus(message.get());
            notification.setOrder(order.get());
            return new ResponseEntity<>(notificationsRepository.save(notification), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
    
    // Vendor Notificaion
    @PostMapping("/vendor/{vendorid}/order/{orderid}/messagestatus/{messagestatusid}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<NotificationsEntity> vendorPost(@RequestBody NotificationsEntity notification, 
                                    @PathVariable("vendorid") Long vendorid,
                                    @PathVariable("orderid") Long orderid,
                                    @PathVariable("messagestatusid") Long messagestatusid) {
        Optional<VendorEntity> vendor = vendorRepository.findById(vendorid);
        Optional<OrderEntity> order = orderRepository.findById(orderid);
        Optional<MessageStatusEntity> message = messageStatusRepository.findById(messagestatusid);
        
        if (vendor.isPresent() && order.isPresent() && message.isPresent()) {
            notification.setVendor(vendor.get());
            notification.setMessageStatus(message.get());
            notification.setOrder(order.get());
            return new ResponseEntity<>(notificationsRepository.save(notification), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        try {
            notificationsRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {}
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Error message")
    public void handleError() {
    }
    
}
