package com.epam.esm.model.repository;

import com.epam.esm.model.entity.GiftCertificate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GiftRepository extends JpaRepository<GiftCertificate, Long> {

    @Query("SELECT DISTINCT gc from GiftCertificate gc inner join Tag t where t.name in (:tagIn)" +
            " and (:giftValue is null or gc.description like :giftValue) or (:giftValue is null or gc.name like :giftValue) GROUP BY gc.id HAVING count(t.name) = :tagsSize")
    List<GiftCertificate> findAllWithParameters(@Param("tagIn") Set<String> tagList, @Param("tagsSize") long tagsLength, @Param("giftValue") String gift, Pageable pageable);

}
