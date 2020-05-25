package com.assesment.vgginternship.menu;

import com.assesment.vgginternship.vendor.VendorEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import lombok.Data;

@Data
@Entity
public class MenuEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private Boolean isRecurring;
    private Integer frequencyOfReocurrence;
    @Temporal(javax.persistence.TemporalType.DATE)
    private java.util.Date dateTimeCreated;
    
    @JsonIgnore
    @ManyToOne
    private VendorEntity vendor;
    
    @PrePersist
    void setDate () {
        dateTimeCreated = new java.util.Date();
    }
}
