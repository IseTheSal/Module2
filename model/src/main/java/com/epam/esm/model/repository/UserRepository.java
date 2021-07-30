package com.epam.esm.model.repository;

import com.epam.esm.model.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);

    @Query("SELECT ord.user.id  FROM Order ord GROUP BY ord.user.id ORDER BY sum(ord.price) DESC")
    List<Long> findUserIdWithMostMoneySpent(Pageable pageable);
}
