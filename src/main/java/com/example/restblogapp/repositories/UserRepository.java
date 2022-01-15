package com.example.restblogapp.repositories;

import com.example.restblogapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;



public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);

    Optional<User> findByUsernameAndPassword(String username, String password);
}
