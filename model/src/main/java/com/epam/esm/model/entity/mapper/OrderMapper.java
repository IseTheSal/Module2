package com.epam.esm.model.entity.mapper;

import com.epam.esm.model.entity.Order;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class OrderMapper implements RowMapper<Order> {
    private static final String ORDER_ID = "id";
    private static final String ORDER_USER_ID = "user_id";
    private static final String ORDER_CERTIFICATE_ID = "certificate_id";
    private static final String ORDER_PRICE = "price";
    private static final String ORDER_DATE = "purchase_date";

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong(ORDER_ID);
        long userId = rs.getLong(ORDER_USER_ID);
        long certificateId = rs.getLong(ORDER_CERTIFICATE_ID);
        BigDecimal price = rs.getBigDecimal(ORDER_PRICE);
        LocalDateTime purchaseDate = rs.getTimestamp(ORDER_DATE).toLocalDateTime();
        return new Order(id, userId, certificateId, price, purchaseDate);
    }
}
