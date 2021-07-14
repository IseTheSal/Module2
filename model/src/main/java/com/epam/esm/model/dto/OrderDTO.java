package com.epam.esm.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDTO extends AuditBasicDTO<OrderDTO> {

    private UserDTO user;
    private GiftCertificateDTO certificate;
    private BigDecimal price;
    private LocalDateTime purchaseDate;

    public OrderDTO() {
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public GiftCertificateDTO getCertificate() {
        return certificate;
    }

    public void setCertificate(GiftCertificateDTO certificate) {
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

        OrderDTO orderDTO = (OrderDTO) o;

        if (user != null ? !user.equals(orderDTO.user) : orderDTO.user != null) return false;
        if (certificate != null ? !certificate.equals(orderDTO.certificate) : orderDTO.certificate != null)
            return false;
        if (price != null ? !price.equals(orderDTO.price) : orderDTO.price != null) return false;
        return purchaseDate != null ? purchaseDate.equals(orderDTO.purchaseDate) : orderDTO.purchaseDate == null;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (certificate != null ? certificate.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (purchaseDate != null ? purchaseDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderDTO{");
        sb.append("user=").append(user);
        sb.append(", certificate=").append(certificate);
        sb.append(", price=").append(price);
        sb.append(", purchaseDate=").append(purchaseDate);
        sb.append('}');
        return sb.toString();
    }
}
