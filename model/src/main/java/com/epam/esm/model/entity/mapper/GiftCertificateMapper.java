package com.epam.esm.model.entity.mapper;

import com.epam.esm.model.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class GiftCertificateMapper implements RowMapper<GiftCertificate> {

    private static final String CERTIFICATE_ID = "id";
    private static final String CERTIFICATE_NAME = "name";
    private static final String CERTIFICATE_DESCRIPTION = "description";
    private static final String CERTIFICATE_PRICE = "price";
    private static final String CERTIFICATE_DURATION = "duration";
    private static final String CERTIFICATE_CREATE_DATE = "create_date";
    private static final String CERTIFICATE_LAST_UPDATE_DATE = "last_update_date";
    private static final String ZONE_ID = "UTC";

    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong(CERTIFICATE_ID);
        String name = rs.getString(CERTIFICATE_NAME);
        String description = rs.getString(CERTIFICATE_DESCRIPTION);
        BigDecimal price = rs.getBigDecimal(CERTIFICATE_PRICE);
        int duration = rs.getInt(CERTIFICATE_DURATION);
        LocalDateTime createDate = rs.getTimestamp(CERTIFICATE_CREATE_DATE).toLocalDateTime();
        LocalDateTime lastUpdateDate = rs.getTimestamp(CERTIFICATE_LAST_UPDATE_DATE).toLocalDateTime();
        return new GiftCertificate(id, name, description, price, duration, createDate, lastUpdateDate);
    }
}
