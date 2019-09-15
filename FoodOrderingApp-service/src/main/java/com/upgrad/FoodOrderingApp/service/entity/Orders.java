package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Integer Id;

    @Column(name = "uuid")
    @NotNull
    @Size(max = 200)
    private String uuid;

    @Column(name = "bill")
    @NotNull
    private Float bill;

    @OneToOne
    @JoinColumn(name = "coupon_id", referencedColumnName = "id")
    private Coupon couponId;

    @Column(name = "discount")
    private Float discount;

    @Column(name = "date")
    @NotNull
    private LocalDateTime date;

    @OneToOne
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    private Payment paymentId;

    @OneToOne(cascade = CascadeType.REMOVE)
    @NotNull
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private CustomerEntity customerId;

    @OneToOne
    @NotNull
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressEntity addressId;

    @OneToOne
    @NotNull
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurantId;

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

    public Float getBill() {
        return bill;
    }

    public void setBill(Float bill) {
        this.bill = bill;
    }

    public Coupon getCouponId() {
        return couponId;
    }

    public void setCouponId(Coupon couponId) {
        this.couponId = couponId;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Payment getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Payment paymentId) {
        this.paymentId = paymentId;
    }

    public CustomerEntity getCustomerId() {
        return customerId;
    }

    public void setCustomerId(CustomerEntity customerId) {
        this.customerId = customerId;
    }

    public AddressEntity getAddressId() {
        return addressId;
    }

    public void setAddressId(AddressEntity addressId) {
        this.addressId = addressId;
    }

    public Restaurant getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Restaurant restaurantId) {
        this.restaurantId = restaurantId;
    }
}
