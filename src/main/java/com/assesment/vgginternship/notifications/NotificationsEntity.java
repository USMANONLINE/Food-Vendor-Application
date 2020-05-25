package com.assesment.vgginternship.notifications;

import com.assesment.vgginternship.customer.CustomerEntity;
import com.assesment.vgginternship.messagestatus.MessageStatusEntity;
import com.assesment.vgginternship.order.OrderEntity;
import com.assesment.vgginternship.vendor.VendorEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import lombok.Data;

@Data
@Entity
public class NotificationsEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String message;
    @Temporal(javax.persistence.TemporalType.DATE)
    private java.util.Date dateTimeCreated;
    
    @ManyToOne
    private CustomerEntity customer;
    @ManyToOne
    private VendorEntity vendor;
    @ManyToOne
    private OrderEntity order;
    @OneToOne
    private MessageStatusEntity messageStatus;
    
    @PrePersist
    void setDate () {
        dateTimeCreated = new java.util.Date();
    }
}
