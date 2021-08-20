package com.epam.esm.model.entity;

import com.epam.esm.model.entity.audit.AuditEntity;
import com.epam.esm.model.entity.audit.AuditListener;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@EntityListeners(AuditListener.class)
@Table(name = "orders")
public class Order extends AuditEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "certificate_id")
    private GiftCertificate certificate;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "purchase_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime purchaseDate;

    public Order() {
    }

    public Order(User user, GiftCertificate certificate, BigDecimal price, LocalDateTime purchaseDate) {
        this.user = user;
        this.certificate = certificate;
        this.price = price;
        this.purchaseDate = purchaseDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GiftCertificate getCertificate() {
        return certificate;
    }

    public void setCertificate(GiftCertificate certificate) {
        this.certificate = certificate;
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
        if (!super.equals(o)) return false;

        Order order = (Order) o;

        if (getId() != order.getId()) return false;
        if (user != null ? !user.equals(order.user) : order.user != null) return false;
        if (certificate != null ? !certificate.equals(order.certificate) : order.certificate != null) return false;
        if (price != null ? !price.equals(order.price) : order.price != null) return false;
        return purchaseDate != null ? purchaseDate.equals(order.purchaseDate) : order.purchaseDate == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (certificate != null ? certificate.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (purchaseDate != null ? purchaseDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("id=").append(getId());
        sb.append(", user=").append(user);
        sb.append(", certificate=").append(certificate);
        sb.append(", price=").append(price);
        sb.append(", purchaseDate=").append(purchaseDate);
        sb.append('}');
        return sb.toString();
    }
}
