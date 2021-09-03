package com.epam.esm.model.repository;

import com.epam.esm.model.entity.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String name);

    @Query("SELECT t" +
            " FROM Order ord" +
            " INNER JOIN ord.certificate.tags t" +
            " WHERE ord.user.id = :userId " +
            " GROUP BY t.id" +
            " ORDER BY count(t.id) DESC")
    List<Tag> findMostWidelyUsedTag(@Param("userId") long userId, Pageable pageable);
}
