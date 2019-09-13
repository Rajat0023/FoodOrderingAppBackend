package com.upgrad.FoodOrderingApp.service.entity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "address")
public class AddressEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Integer Id;

    @Column(name= "uuid")
    @NotNull
    @Size(max = 200)
    private String uuid;

    @Column(name="flat_buil_number")
    @Size(max = 255)
    private String flatNumber;

    @Column(name="locality")
    @Size(max = 255)
    private String locality;

    @Column(name="city")
    @Size(max = 30)
    private String city;

    @Column(name="pincode")
    @Size(max = 30)
    private String pinCode;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "state_id",referencedColumnName = "id")
    private State stateId;


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public State getStateId() {
        return stateId;
    }

    public void setStateId(State stateId) {
        this.stateId = stateId;
    }

}
