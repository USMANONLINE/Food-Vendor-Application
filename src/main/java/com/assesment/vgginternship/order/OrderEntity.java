package com.assesment.vgginternship.order;

import com.assesment.vgginternship.customer.CustomerEntity;
import com.assesment.vgginternship.menu.MenuEntity;
import com.assesment.vgginternship.orderstatus.OrderStatusEntity;
import com.assesment.vgginternship.vendor.VendorEntity;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import lombok.Data;

@Data
@Entity
public class OrderEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
    private Double amountDue;
    private Double amountPaid;
    private Double amountOutstanding;
    @Temporal(javax.persistence.TemporalType.DATE)
    private java.util.Date dateAndTimeOfOrder;
    
    @OneToOne
    private OrderStatusEntity orderStatus;
    @OneToMany
    private List<MenuEntity> itemsOrdered;
    @ManyToOne
    private CustomerEntity customer;
    @ManyToOne
    private VendorEntity vendor;
    
    @PrePersist
    void setDate () {
        dateAndTimeOfOrder = new java.util.Date();
    }
    
}
