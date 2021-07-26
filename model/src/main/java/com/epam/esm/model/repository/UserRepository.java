package com.epam.esm.model.repository;

import com.epam.esm.model.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);

    @Query("SELECT ord.user FROM Order ord GROUP BY ord.user ORDER BY sum(ord.price) DESC")
    List<User> findUserIdWithMostMoneySpent(Pageable pageable);
}
