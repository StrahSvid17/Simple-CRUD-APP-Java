package com.example.simplecrudjava.repository;

import com.example.simplecrudjava.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    @Transactional
    @Query("update users_tbl u set u.username = ?1, u.email = ?2 where u.id = ?3")
    void updateUser(String username, String email, Long id);
}
