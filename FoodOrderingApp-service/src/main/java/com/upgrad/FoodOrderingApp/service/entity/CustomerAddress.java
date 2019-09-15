package com.upgrad.FoodOrderingApp.service.entity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "customer_address")
public class CustomerAddress {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Integer Id;

    @OneToOne(cascade = CascadeType.REMOVE)
    @NotNull
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressEntity addressId;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @NotNull
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private CustomerEntity customerId;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public AddressEntity getAddressId() {
        return addressId;
    }

    public void setAddressId(AddressEntity addressId) {
        this.addressId = addressId;
    }

    public CustomerEntity getCustomerId() {
        return customerId;
    }

    public void setCustomerId(CustomerEntity customerId) {
        this.customerId = customerId;
    }
}
