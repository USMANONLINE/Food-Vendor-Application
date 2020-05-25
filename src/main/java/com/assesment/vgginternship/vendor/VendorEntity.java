package com.assesment.vgginternship.vendor;

import com.assesment.vgginternship.auth.AuthEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import lombok.Data;

@Data
@Entity
public class VendorEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String businessName;
    private String email;
    private String phoneNumber;
    @Temporal(javax.persistence.TemporalType.DATE)
    private java.util.Date dateTimeCreated;
    
    @OneToOne(optional = false, orphanRemoval = true)
    private AuthEntity auth;
//    @OneToMany
//    private List<MenuEntity> menus;
    
    @PrePersist
    void setDate () {
        dateTimeCreated = new java.util.Date();
    }
}
