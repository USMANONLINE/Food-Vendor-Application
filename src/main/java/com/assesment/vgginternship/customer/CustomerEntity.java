package com.assesment.vgginternship.customer;

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
public class CustomerEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private Double amountOutstanding;
    @Temporal(javax.persistence.TemporalType.DATE)
    private java.util.Date dateTimeCreated;
    
    @OneToOne
    private AuthEntity auth;
    
    @PrePersist
    void setDate () {
        dateTimeCreated = new java.util.Date();
    }
}
