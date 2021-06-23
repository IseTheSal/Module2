package com.epam.esm.model.dao.impl;

public class SqlQueryHolder {

    static final String ID_KEY_HOLDER = "id";
    static final String CREATE_CERTIFICATE = "INSERT INTO gift_certificates" +
            " (id, name, description, price, duration, create_date, last_update_date)" +
            " VALUES(default, ?, ?, ?, ?, now(), now())";
    static final String ATTACH_ID_VALUES = "INSERT INTO certificate_tag(certificate_id, tag_id) VALUES(?,?)";
    static final String DELETE_CERTIFICATE = "DELETE FROM gift_certificates WHERE id = ?";
    static final String FIND_ALL_CERTIFICATE_TAGS = "SELECT id, name FROM tags" +
            " INNER JOIN certificate_tag ct on tags.id = ct.tag_id WHERE ct.certificate_id = ?";
    static final String FIND_ALL_CERTIFICATES = "SELECT gift_certificates.id, gift_certificates.name," +
            " description, price, duration, create_date," +
            " last_update_date FROM gift_certificates";
    static final String FIND_ALL_DISTINCT_CERTIFICATES = "SELECT DISTINCT gift_certificates.id, gift_certificates.name," +
            " description, price, duration, create_date," +
            " last_update_date FROM gift_certificates";
    static final String FIND_BY_TAG_NAME_CLAUSE = " INNER JOIN certificate_tag ct on gift_certificates.id = ct.certificate_id" +
            " INNER JOIN tags t on t.id = ct.tag_id" +
            " WHERE t.name = ?";
    static final String AND_DESCRIPTION_NAME_LIKE_CLAUSE = " AND (description LIKE ? OR gift_certificates.name LIKE ?)";
    static final String WHERE_DESCRIPTION_NAME_LIKE_CLAUSE = " WHERE description LIKE ? OR gift_certificates.name LIKE ?";
    static final String ORDER_BY_CLAUSE = " ORDER BY";
    static final String CREATE_DATE = " create_date";
    static final String GIFT_NAME = " gift_certificates.name";
    static final String COMMA = ", ";
    static final String FIND_CERTIFICATE_BY_ID = FIND_ALL_CERTIFICATES + " WHERE id = ?";
    static final String UPDATE_CERTIFICATE = "UPDATE gift_certificates" +
            " SET name = COALESCE(?, name)," +
            " description = COALESCE(?, description)," +
            " price = COALESCE(?, price)," +
            " duration = COALESCE(?, duration)," +
            " last_update_date = now()" +
            " WHERE id = ?";
    static final String FIND_TAG_BY_ID = "SELECT id, name FROM tags WHERE id = ?";
    static final String FIND_TAG_BY_NAME = "SELECT id, name FROM tags WHERE name = ?";
    static final String FIND_ALL_TAGS = "SELECT id, name FROM tags";
    static final String DELETE_TAG_BY_ID = "DELETE FROM tags where id = ?";
    static final String CREATE_TAG = "INSERT INTO tags(id, name) VALUES(default, ?)";
}
