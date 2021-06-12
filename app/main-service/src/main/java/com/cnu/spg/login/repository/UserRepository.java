package com.cnu.spg.login.repository;

import com.cnu.spg.login.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);

    boolean existsByUserName(String userName);

    void deleteByUserName(String userName);
}
