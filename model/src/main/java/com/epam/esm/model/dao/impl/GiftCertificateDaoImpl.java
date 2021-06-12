package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.mapper.GiftCertificateMapper;
import com.epam.esm.model.entity.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private static final String CREATE_CERTIFICATE = "INSERT INTO gift_certificates" +
            "(id, name, description, price, duration, create_date, last_update_date)" +
            "VALUES(default, ?, ?, ?, ?, ?, ?)";
    private static final String ATTACH_ID_VALUES = "INSERT INTO certificate_tag(certificate_id, tag_id) VALUES(?,?)";
    private static final String DELETE_CERTIFICATE = "DELETE FROM gift_certificates WHERE id = ?";
    private static final String ID_KEY_HOLDER = "id";
    private static final String FIND_CERTIFICATE_BY_ID = "SELECT id, name, description, price, duration, create_date, last_update_date FROM gift_certificates WHERE id = ?";
    private static final String FIND_ALL_CERTIFICATE_TAGS = "SELECT id, name FROM tags INNER JOIN certificate_tag ct on tags.id = ct.tag_id WHERE ct.certificate_id = ?";
    private static final String FIND_ALL_CERTIFICATES = "SELECT id, name, description, price, duration, create_date, last_update_date FROM gift_certificates";
    private static final String UPDATE_CERTIFICATE = "UPDATE gift_certificates " +
            "SET name = COALESCE(?, name), " +
            "description = COALESCE(?, description), " +
            "price = COALESCE(?, price), " +
            "duration = COALESCE(?, duration), " +
            "last_update_date = ? " +
            "WHERE id = ? " +
            "AND (? IS NOT NULL AND ? IS DISTINCT FROM name OR " +
            "? IS NOT NULL AND ? IS DISTINCT FROM description OR " +
            "? IS NOT NULL AND ? IS DISTINCT FROM price OR " +
            "? IS NOT NULL AND ? IS DISTINCT FROM duration);";


    private final JdbcTemplate jdbcTemplate;
    private final TagDao tagDao;
    private TransactionTemplate transactionTemplate;

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, TagDao tagDao, PlatformTransactionManager transactionManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagDao = tagDao;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Override
    public GiftCertificate create(GiftCertificate certificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        return transactionTemplate.execute(status -> {
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CERTIFICATE, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, certificate.getName());
                preparedStatement.setString(2, certificate.getDescription());
                preparedStatement.setBigDecimal(3, certificate.getPrice());
                preparedStatement.setInt(4, certificate.getDuration());
                preparedStatement.setTimestamp(5, Timestamp.from(certificate.getCreateDate().toInstant()));
                preparedStatement.setTimestamp(6, Timestamp.from(certificate.getLastUpdateDate().toInstant()));
                return preparedStatement;
            }, keyHolder);
            long id = (long) keyHolder.getKeys().get(ID_KEY_HOLDER);
            attachTagToCertificate(id, certificate.getTags());
            certificate.setId(id);
            return findById(id).get();
        });
    }

    private void attachTagToCertificate(long certificateId, Set<Tag> tagSet) {
        for (Tag tag : tagSet) {
            Optional<Tag> optionalTag = tagDao.findByName(tag.getName());
            long tagId;
            if (optionalTag.isPresent()) {
                tagId = optionalTag.get().getId();
            } else {
                Tag newTag = tagDao.create(tag);
                tagId = newTag.getId();
            }
            tag.setId(tagId);
            attachIdValues(certificateId, tagId);
        }
    }

    private void attachIdValues(long certificateId, long tagId) {
        jdbcTemplate.update(ATTACH_ID_VALUES, certificateId, tagId);
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        List<GiftCertificate> giftCertificateList = jdbcTemplate.query(FIND_CERTIFICATE_BY_ID, new GiftCertificateMapper(), id);
        if (!giftCertificateList.isEmpty()) {
            GiftCertificate giftCertificate = giftCertificateList.get(0);
            long giftCertificateId = giftCertificate.getId();
            List<Tag> tagList = findTagsByCertificateId(giftCertificateId);
            tagList.forEach(giftCertificate::addTag);
            return Optional.of(giftCertificate);
        }
        return Optional.empty();
    }

    @Override
    public List<Tag> findTagsByCertificateId(long id) {
        return jdbcTemplate.query(FIND_ALL_CERTIFICATE_TAGS, new TagMapper(), id);
    }

    @Override
    public List<GiftCertificate> findAll() {
        List<GiftCertificate> giftList = jdbcTemplate.query(FIND_ALL_CERTIFICATES, new GiftCertificateMapper());
        giftList.forEach(giftCertificate -> {
            long id = giftCertificate.getId();
            List<Tag> tagList = findTagsByCertificateId(id);
            tagList.forEach(giftCertificate::addTag);
        });
        return giftList;
    }

    @Override
    public boolean delete(long id) {
        return (jdbcTemplate.update(DELETE_CERTIFICATE, id) > 0);
    }

    @Override
    public GiftCertificate update(GiftCertificate certificate) {
        return transactionTemplate.execute(status -> {
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CERTIFICATE, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, certificate.getName());
                preparedStatement.setString(2, certificate.getDescription());
                preparedStatement.setBigDecimal(3, certificate.getPrice());
                preparedStatement.setObject(4, certificate.getDuration());
                preparedStatement.setTimestamp(5, Timestamp.from(certificate.getLastUpdateDate().toInstant()));
                preparedStatement.setLong(6, certificate.getId());
                preparedStatement.setString(7, certificate.getName());
                preparedStatement.setString(8, certificate.getName());
                preparedStatement.setString(9, certificate.getDescription());
                preparedStatement.setString(10, certificate.getDescription());
                preparedStatement.setBigDecimal(11, certificate.getPrice());
                preparedStatement.setBigDecimal(12, certificate.getPrice());
                preparedStatement.setObject(13, certificate.getDuration());
                preparedStatement.setObject(14, certificate.getDuration());
                return preparedStatement;
            });
            attachTagToCertificate(certificate.getId(), certificate.getTags());
            return findById(certificate.getId()).get();
        });
    }
}
