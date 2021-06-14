package com.epam.esm.model.dao.impl;

public class SqlQueryHolder {

    static final String ID_KEY_HOLDER = "id";
    static final String CREATE_CERTIFICATE = "INSERT INTO gift_certificates" +
            " (id, name, description, price, duration, create_date, last_update_date)" +
            " VALUES(default, ?, ?, ?, ?, ?, ?)";
    static final String ATTACH_ID_VALUES = "INSERT INTO certificate_tag(certificate_id, tag_id) VALUES(?,?)";
    static final String DELETE_CERTIFICATE = "DELETE FROM gift_certificates WHERE id = ?";
    static final String FIND_ALL_CERTIFICATE_TAGS = "SELECT id, name FROM tags" +
            " INNER JOIN certificate_tag ct on tags.id = ct.tag_id WHERE ct.certificate_id = ?";
    static final String FIND_ALL_CERTIFICATES = "SELECT gift_certificates.id, gift_certificates.name," +
            " description, price, duration, create_date," +
            " last_update_date FROM gift_certificates";
    static final String FIND_CERTIFICATE_BY_ID = FIND_ALL_CERTIFICATES + " WHERE id = ?";
    static final String UPDATE_CERTIFICATE = "UPDATE gift_certificates" +
            " SET name = COALESCE(?, name)," +
            " description = COALESCE(?, description)," +
            " price = COALESCE(?, price)," +
            " duration = COALESCE(?, duration)," +
            " last_update_date = ?" +
            " WHERE id = ?" +
            " AND (? IS NOT NULL AND ? IS DISTINCT FROM name OR" +
            " ? IS NOT NULL AND ? IS DISTINCT FROM description OR" +
            " ? IS NOT NULL AND ? IS DISTINCT FROM price OR" +
            " ? IS NOT NULL AND ? IS DISTINCT FROM duration)";
    static final String FIND_BY_PART_NAME_OR_DESCRIPTION = FIND_ALL_CERTIFICATES +
            " WHERE position(? in description) > 0 OR position(? in name) > 0";
    static final String FIND_CERTIFICATES_BY_TAG_NAME = FIND_ALL_CERTIFICATES +
            " INNER JOIN certificate_tag ct on gift_certificates.id = ct.certificate_id" +
            " INNER JOIN tags t on t.id = ct.tag_id " +
            " WHERE t.name = ?";

    static final String FIND_TAG_BY_ID = "SELECT id, name FROM tags WHERE id = ?";
    static final String FIND_TAG_BY_NAME = "SELECT id, name FROM tags WHERE name = ?";
    static final String FIND_ALL_TAGS = "SELECT id, name FROM tags";
    static final String DELETE_TAG_BY_ID = "DELETE FROM tags where id = ?";
    static final String CREATE_TAG = "INSERT INTO tags(id, name) VALUES(default, ?)";
}
