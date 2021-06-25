package com.epam.esm.model.entity;

import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Order extends RepresentationModel<Order> implements Entity {

    private long id;
    private long userId;
    private long certificateId;
    private BigDecimal price;
    private LocalDateTime purchaseDate;

    public Order() {
    }

    public Order(long orderId, long userId, long certificateId, BigDecimal price, LocalDateTime purchaseDate) {
        this.id = orderId;
        this.userId = userId;
        this.certificateId = certificateId;
        this.price = price;
        this.purchaseDate = purchaseDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(long certificateId) {
        this.certificateId = certificateId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        if (userId != order.userId) return false;
        if (certificateId != order.certificateId) return false;
        if (price != null ? !price.equals(order.price) : order.price != null) return false;
        return purchaseDate != null ? purchaseDate.equals(order.purchaseDate) : order.purchaseDate == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (certificateId ^ (certificateId >>> 32));
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (purchaseDate != null ? purchaseDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("orderId=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", certificateId=").append(certificateId);
        sb.append(", price=").append(price);
        sb.append(", purchaseDate=").append(purchaseDate);
        sb.append('}');
        return sb.toString();
    }
}
