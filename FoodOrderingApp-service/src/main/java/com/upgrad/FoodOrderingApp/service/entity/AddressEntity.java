package com.upgrad.FoodOrderingApp.service.entity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "address")
@NamedQueries({
  @NamedQuery(
      name = "findAddressByUUID",
      query = "Select a from AddressEntity a where a.uuid = :uuid")
})
public class AddressEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer Id;

  @Column(name = "uuid")
  @NotNull
  @Size(max = 200)
  private String uuid;

  @Column(name = "flat_buil_number")
  @Size(max = 255)
  private String flatBuilNo;

  @Column(name = "locality")
  @Size(max = 255)
  private String locality;

  @Column(name = "city")
  @Size(max = 30)
  private String city;

  @Column(name = "pincode")
  @Size(max = 30)
  private String pinCode;

  @ManyToOne
  @JoinColumn(name = "state_id", referencedColumnName = "id")
  private StateEntity state;

  @Column(name = "active")
  private Integer active;

  @OneToOne(mappedBy = "address")
  private CustomerAddress customerAddress;

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

  public String getFlatBuilNo() {
    return flatBuilNo;
  }

  public void setFlatBuilNo(String flatBuilNo) {
    this.flatBuilNo = flatBuilNo;
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

  public StateEntity getState() {
    return state;
  }

  public void setState(StateEntity state) {
    this.state = state;
  }

  public CustomerAddress getCustomerAddress() {
    return customerAddress;
  }

  public void setCustomerAddress(CustomerAddress customerAddress) {
    this.customerAddress = customerAddress;
  }

  public Integer getActive() {
    return active;
  }

  public void setActive(Integer active) {
    this.active = active;
  }
  }
