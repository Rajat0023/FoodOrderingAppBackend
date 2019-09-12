package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "order_item")
public class OrderItem {

      @Id
      @Column(name = "id")
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      @NotNull
      private Integer Id;

      @ManyToOne(cascade = CascadeType.REMOVE)
      @NotNull
      @JoinColumn(name = "order_id", referencedColumnName = "id")
      private Orders orderId;

      @OneToOne
      @NotNull
      @JoinColumn(name = "item_id", referencedColumnName = "id")
      private Item itemId;

      @Column(name = "quantity")
      @NotNull
      private Integer quantity;

      @Column(name = "price")
      @NotNull
      private Integer price;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Orders getOrderId() {
        return orderId;
    }

    public void setOrderId(Orders orderId) {
        this.orderId = orderId;
    }

    public Item getItemId() {
        return itemId;
    }

    public void setItemId(Item itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}


