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

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private static final String PERCENT_SYMBOL = "%";
    private static final String ASC = " ASC ";
    private static final String DESC = " DESC ";

    private final JdbcTemplate jdbcTemplate;
    private final TagDao tagDao;

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, TagDao tagDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagDao = tagDao;
    }

    @Override
    public GiftCertificate create(GiftCertificate certificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueryHolder.CREATE_CERTIFICATE,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, certificate.getName());
            preparedStatement.setString(2, certificate.getDescription());
            preparedStatement.setBigDecimal(3, certificate.getPrice());
            preparedStatement.setInt(4, certificate.getDuration());
            return preparedStatement;
        }, keyHolder);
        long id = (long) keyHolder.getKeys().get(SqlQueryHolder.ID_KEY_HOLDER);
        attachTagToCertificate(id, certificate.getTags());
        certificate.setId(id);
        return findById(id).get();
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
    public List<GiftCertificate> findAll(int amount, int page) {
        List<GiftCertificate> giftList = jdbcTemplate.query(SqlQueryHolder.FIND_ALL_CERTIFICATES
                        + SqlQueryHolder.PAGE_LIMIT_OFFSET,
                new GiftCertificateMapper(), amount, page);
        return attachTagsToCertificate(giftList);
    }

    @Override
    public boolean delete(long id) {
        return (jdbcTemplate.update(SqlQueryHolder.DELETE_CERTIFICATE, id) > 0);
    }

    @Override
    public GiftCertificate update(GiftCertificate certificate) {
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(SqlQueryHolder.UPDATE_CERTIFICATE,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, certificate.getName());
            preparedStatement.setString(2, certificate.getDescription());
            preparedStatement.setBigDecimal(3, certificate.getPrice());
            preparedStatement.setObject(4, certificate.getDuration());
            preparedStatement.setLong(5, certificate.getId());
            return preparedStatement;
        });
        attachTagToCertificate(certificate.getId(), certificate.getTags());
        return findById(certificate.getId()).get();
    }

    private List<GiftCertificate> attachTagsToCertificate(List<GiftCertificate> certificateList) {
        certificateList.forEach(giftCertificate -> {
            long id = giftCertificate.getId();
            List<Tag> tagList = findTagsByCertificateId(id);
            tagList.forEach(giftCertificate::addTag);
        });
        return certificateList;
    }

    @Override
    public List<GiftCertificate> findByAttributes(String tagName, String giftValue, String dateOrderType,
                                                  String nameOrderType, int amount, int page) {
        String query = SqlQueryHolder.FIND_ALL_DISTINCT_CERTIFICATES;
        List<String> queryParamValues = new ArrayList<>();
        if ((tagName != null) && (giftValue != null)) {
            query += SqlQueryHolder.FIND_BY_TAG_NAME_CLAUSE + SqlQueryHolder.AND_DESCRIPTION_NAME_LIKE_CLAUSE;
            queryParamValues.add(tagName);
            queryParamValues.add(PERCENT_SYMBOL + giftValue + PERCENT_SYMBOL);
            queryParamValues.add(PERCENT_SYMBOL + giftValue + PERCENT_SYMBOL);
        } else if (giftValue != null) {
            query += SqlQueryHolder.WHERE_DESCRIPTION_NAME_LIKE_CLAUSE;
            queryParamValues.add(PERCENT_SYMBOL + giftValue + PERCENT_SYMBOL);
            queryParamValues.add(PERCENT_SYMBOL + giftValue + PERCENT_SYMBOL);
        } else if (tagName != null) {
            query += SqlQueryHolder.FIND_BY_TAG_NAME_CLAUSE;
            queryParamValues.add(tagName);
        }
        if ((dateOrderType != null) && (nameOrderType != null)) {
            String dateOrderSortType = (dateOrderType.equalsIgnoreCase(DESC.trim()) ? DESC : ASC);
            String dateSortOrder = SqlQueryHolder.CREATE_DATE + dateOrderSortType;
            String nameOrderSortType = (nameOrderType.equalsIgnoreCase(DESC.trim()) ? DESC : ASC);
            String nameSortOrder = SqlQueryHolder.GIFT_NAME + nameOrderSortType;
            query += SqlQueryHolder.ORDER_BY_CLAUSE + dateSortOrder + SqlQueryHolder.COMMA + nameSortOrder;
        } else if (dateOrderType != null) {
            String dateOrderSortType = (dateOrderType.equalsIgnoreCase(DESC.trim()) ? DESC : ASC);
            String dateSortOrder = SqlQueryHolder.CREATE_DATE + dateOrderSortType;
            query += SqlQueryHolder.ORDER_BY_CLAUSE + dateSortOrder;
        } else if (nameOrderType != null) {
            String nameOrderSortType = (nameOrderType.equalsIgnoreCase(DESC.trim()) ? DESC : ASC);
            String nameSortOrder = SqlQueryHolder.GIFT_NAME + nameOrderSortType;
            query += SqlQueryHolder.ORDER_BY_CLAUSE + nameSortOrder;
        }
        query += SqlQueryHolder.PAGE_LIMIT_OFFSET;
        queryParamValues.add(String.valueOf(amount));
        queryParamValues.add(String.valueOf(page));
        Object[] paramValues = queryParamValues.toArray();
        List<GiftCertificate> certificateList = jdbcTemplate.query(query, new GiftCertificateMapper(), paramValues);
        return attachTagsToCertificate(certificateList);
    }


    @Override
    public List<GiftCertificate> findBySeveralTags(String[] tagNames, int amount, int page) {
        StringBuilder tag = new StringBuilder(SqlQueryHolder.OPEN_BRACKET);
        for (int i = 0; i < tagNames.length; i++) {
            tag.append(SqlQueryHolder.APOSTROPHE).append(tagNames[i]).append(SqlQueryHolder.APOSTROPHE);
            if (i != tagNames.length - 1) {
                tag.append(SqlQueryHolder.COMMA);
            }
        }
        tag.append(SqlQueryHolder.CLOSE_BRACKET);
        String query = SqlQueryHolder.FIND_CERTIFICATES_WITH_TAGS_START + tag
                + SqlQueryHolder.FIND_CERTIFICATES_WITH_TAGS_END + SqlQueryHolder.PAGE_LIMIT_OFFSET;
        List<GiftCertificate> certificateList = jdbcTemplate.query(query, new GiftCertificateMapper(), tagNames.length, amount, page);
        attachTagsToCertificate(certificateList);
        return certificateList;
    }
}
