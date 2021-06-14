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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private final JdbcTemplate jdbcTemplate;
    private final TagDao tagDao;
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, TagDao tagDao, PlatformTransactionManager manager) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagDao = tagDao;
        this.transactionTemplate = new TransactionTemplate(manager);
    }

    @Override
    public GiftCertificate create(GiftCertificate certificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        return transactionTemplate.execute(status -> {
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(SqlQueryHolder.CREATE_CERTIFICATE,
                        Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, certificate.getName());
                preparedStatement.setString(2, certificate.getDescription());
                preparedStatement.setBigDecimal(3, certificate.getPrice());
                preparedStatement.setInt(4, certificate.getDuration());
                preparedStatement.setTimestamp(5, Timestamp.from(certificate.getCreateDate()
                        .toInstant()));
                preparedStatement.setTimestamp(6, Timestamp.from(certificate.getLastUpdateDate()
                        .toInstant()));
                return preparedStatement;
            }, keyHolder);
            long id = (long) keyHolder.getKeys().get(SqlQueryHolder.ID_KEY_HOLDER);
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
        jdbcTemplate.update(SqlQueryHolder.ATTACH_ID_VALUES, certificateId, tagId);
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        List<GiftCertificate> giftCertificateList = jdbcTemplate.query(SqlQueryHolder.FIND_CERTIFICATE_BY_ID,
                new GiftCertificateMapper(), id);
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
        return jdbcTemplate.query(SqlQueryHolder.FIND_ALL_CERTIFICATE_TAGS, new TagMapper(), id);
    }

    @Override
    public List<GiftCertificate> findAll() {
        List<GiftCertificate> giftList = jdbcTemplate.query(SqlQueryHolder.FIND_ALL_CERTIFICATES,
                new GiftCertificateMapper());
        return attachTagsToCertificate(giftList);
    }

    @Override
    public boolean delete(long id) {
        return (jdbcTemplate.update(SqlQueryHolder.DELETE_CERTIFICATE, id) > 0);
    }

    @Override
    public GiftCertificate update(GiftCertificate certificate) {
        return transactionTemplate.execute(status -> {
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(SqlQueryHolder.UPDATE_CERTIFICATE,
                        Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, certificate.getName());
                preparedStatement.setString(2, certificate.getDescription());
                preparedStatement.setBigDecimal(3, certificate.getPrice());
                preparedStatement.setObject(4, certificate.getDuration());
                preparedStatement.setTimestamp(5, Timestamp.from(certificate.getLastUpdateDate()
                        .toInstant()));
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

    @Override
    public List<GiftCertificate> findByNameOrDescription(String searchValue) {
        List<GiftCertificate> certificateList = jdbcTemplate.query(SqlQueryHolder.FIND_BY_PART_NAME_OR_DESCRIPTION,
                new GiftCertificateMapper(), searchValue, searchValue);
        return attachTagsToCertificate(certificateList);
    }

    @Override
    public List<GiftCertificate> findByTag(String tagName) {
        List<GiftCertificate> certificateList = jdbcTemplate.query(SqlQueryHolder.FIND_CERTIFICATES_BY_TAG_NAME,
                new GiftCertificateMapper(), tagName);
        return attachTagsToCertificate(certificateList);
    }

    private List<GiftCertificate> attachTagsToCertificate(List<GiftCertificate> certificateList) {
        certificateList.forEach(giftCertificate -> {
            long id = giftCertificate.getId();
            List<Tag> tagList = findTagsByCertificateId(id);
            tagList.forEach(giftCertificate::addTag);
        });
        return certificateList;
    }
}
