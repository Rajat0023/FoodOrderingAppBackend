package com.upgrad.FoodOrderingApp.service.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@NamedQuery(name="findOrdersByCustomerId",query = "select o from OrdersEntity o where o.customerId.uuid =:customerId")
public class OrdersEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(name = "uuid")
    @NotNull
    @Size(max = 200)
    private String uuid;

    @Column(name = "bill")
    @NotNull
    private BigDecimal bill;


    @OneToOne
    @JoinColumn(name = "coupon_id", referencedColumnName = "id")
    private CouponEntity couponEntityId;

    @Column(name = "discount")
    private BigDecimal discount;

    @Column(name = "date")
    @NotNull
    private LocalDateTime date;

    @OneToOne
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    private PaymentEntity paymentEntityId;

    @OneToOne(cascade = CascadeType.REMOVE)                   //this cascades the operation to child entity too(in this case only delete operation)
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
    private RestaurantEntity restaurantId;

//bidirectional association
    @OneToMany(mappedBy = "orderId",fetch=FetchType.LAZY,cascade = CascadeType.PERSIST)
   @OnDelete(action = OnDeleteAction.CASCADE) //deleting foreign key, when primary key itself deleted
    private List<OrderItemEntity> orderItems;   //Doubt //how to get this transient child-entity also persisted



    public List<OrderItemEntity> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemEntity> orderItems) {
        this.orderItems = orderItems;
    }

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

    public BigDecimal getBill() {
        return bill;
    }

    public void setBill(BigDecimal bill) {
        this.bill = bill;
    }

    public CouponEntity getCouponEntityId() {
        return couponEntityId;
    }

    public void setCouponEntityId(CouponEntity couponEntityId) {
        this.couponEntityId = couponEntityId;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public PaymentEntity getPaymentEntityId() {
        return paymentEntityId;
    }

    public void setPaymentEntityId(PaymentEntity paymentEntityId) {
        this.paymentEntityId = paymentEntityId;
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

    public RestaurantEntity getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(RestaurantEntity restaurantId) {
        this.restaurantId = restaurantId;
    }
}
