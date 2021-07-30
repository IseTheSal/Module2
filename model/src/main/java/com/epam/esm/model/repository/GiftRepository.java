package com.epam.esm.model.repository;

import com.epam.esm.model.entity.GiftCertificate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface GiftRepository extends JpaRepository<GiftCertificate, Long> {

    @Query("SELECT gc from GiftCertificate gc inner join gc.tags t where (:tagsSize = 0L or t.name in (:tagIn))" +
            " and ((:giftValue is null or gc.description like :giftValue) or (:giftValue is null or gc.name like :giftValue)) GROUP BY gc.id HAVING count(t.name) >= :tagsSize")
    List<GiftCertificate> findAllWithParameters(@Param("tagIn") Set<String> tagSet, @Param("tagsSize") Long tagsLength, @Param("giftValue") String gift, Pageable pageable);
}
