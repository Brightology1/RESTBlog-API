package com.example.restblogapp.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.restblogapp.entities.User;
import com.example.restblogapp.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserService.class})
@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void testPasswordEncoder() {
        assertTrue(
                this.userService.passwordEncoder() instanceof org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder);
    }

    @Test
    void testSave() {
        User user = new User();
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");
        when(this.userRepository.save((User) any())).thenReturn(user);

        User user1 = new User();
        user1.setPassword("iloveyou");
        user1.setRoles(new ArrayList<>());
        user1.setUsername("janedoe");
        this.userService.save(user1);
        verify(this.userRepository).save((User) any());
        assertTrue(this.userService.getAllUsers().isEmpty());
    }

    @Test
    void testGetUser() {
        User user = new User();
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");
        when(this.userRepository.findByUsername((String) any())).thenReturn(user);
        assertSame(user, this.userService.getUser("janedoe"));
        verify(this.userRepository).findByUsername((String) any());
        assertTrue(this.userService.getAllUsers().isEmpty());
    }

    @Test
    void testGetAllUsers() {
        ArrayList<User> userList = new ArrayList<>();
        when(this.userRepository.findAll()).thenReturn(userList);
        List<User> actualAllUsers = this.userService.getAllUsers();
        assertSame(userList, actualAllUsers);
        assertTrue(actualAllUsers.isEmpty());
        verify(this.userRepository).findAll();
    }
}

