package com.example.restblogapp.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.restblogapp.entities.Post;
import com.example.restblogapp.entities.User;
import com.example.restblogapp.repositories.PostRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PostService.class})
@ExtendWith(SpringExtension.class)
class PostServiceTest {
    @MockBean
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Test
    void testGetAllPosts() {
        ArrayList<Post> postList = new ArrayList<>();
        when(this.postRepository.findAll()).thenReturn(postList);
        List<Post> actualAllPosts = this.postService.getAllPosts();
        assertSame(postList, actualAllPosts);
        assertTrue(actualAllPosts.isEmpty());
        verify(this.postRepository).findAll();
    }

    @Test
    void testGetAllPosts2() {
        when(this.postRepository.findAll()).thenThrow(new RuntimeException("An error occurred"));
        assertThrows(RuntimeException.class, () -> this.postService.getAllPosts());
        verify(this.postRepository).findAll();
    }

    @Test
    void testInsert() {
        User user = new User();
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");

        Post post = new Post();
        post.setBody("Not all who wander are lost");
        post.setCreator(user);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        post.setDateCreated(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        post.setId(123L);
        post.setTitle("Dr");
        when(this.postRepository.save((Post) any())).thenReturn(post);

        User user1 = new User();
        user1.setPassword("iloveyou");
        user1.setRoles(new ArrayList<>());
        user1.setUsername("janedoe");

        Post post1 = new Post();
        post1.setBody("Not all who wander are lost");
        post1.setCreator(user1);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        post1.setDateCreated(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        post1.setId(123L);
        post1.setTitle("Dr");
        this.postService.insert(post1);
        verify(this.postRepository).save((Post) any());
        assertTrue(this.postService.getAllPosts().isEmpty());
    }

    @Test
    void testInsert2() {
        when(this.postRepository.save((Post) any())).thenThrow(new RuntimeException("An error occurred"));

        User user = new User();
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");

        Post post = new Post();
        post.setBody("Not all who wander are lost");
        post.setCreator(user);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        post.setDateCreated(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        post.setId(123L);
        post.setTitle("Dr");
        assertThrows(RuntimeException.class, () -> this.postService.insert(post));
        verify(this.postRepository).save((Post) any());
    }

    @Test
    void testFindByUser() {
        ArrayList<Post> postList = new ArrayList<>();
        when(this.postRepository.findByCreatorId((Long) any())).thenReturn(postList);

        User user = new User();
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");
        List<Post> actualFindByUserResult = this.postService.findByUser(user);
        assertSame(postList, actualFindByUserResult);
        assertTrue(actualFindByUserResult.isEmpty());
        verify(this.postRepository).findByCreatorId((Long) any());
        assertTrue(this.postService.getAllPosts().isEmpty());
    }

    @Test
    void testFindByUser2() {
        when(this.postRepository.findByCreatorId((Long) any())).thenThrow(new RuntimeException("An error occurred"));

        User user = new User();
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");
        assertThrows(RuntimeException.class, () -> this.postService.findByUser(user));
        verify(this.postRepository).findByCreatorId((Long) any());
    }

    @Test
    void testDeletePost() {
        User user = new User();
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");

        Post post = new Post();
        post.setBody("Not all who wander are lost");
        post.setCreator(user);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        post.setDateCreated(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        post.setId(123L);
        post.setTitle("Dr");
        Optional<Post> ofResult = Optional.of(post);
        doNothing().when(this.postRepository).deleteById((Long) any());
        when(this.postRepository.findById((Long) any())).thenReturn(ofResult);
        assertTrue(this.postService.deletePost(123L));
        verify(this.postRepository).deleteById((Long) any());
        verify(this.postRepository).findById((Long) any());
        assertTrue(this.postService.getAllPosts().isEmpty());
    }

    @Test
    void testDeletePost2() {
        doNothing().when(this.postRepository).deleteById((Long) any());
        when(this.postRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> this.postService.deletePost(123L));
        verify(this.postRepository).findById((Long) any());
    }

    @Test
    void testGetPost() {
        User user = new User();
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");

        Post post = new Post();
        post.setBody("Not all who wander are lost");
        post.setCreator(user);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        post.setDateCreated(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        post.setId(123L);
        post.setTitle("Dr");
        Optional<Post> ofResult = Optional.of(post);
        when(this.postRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(post, this.postService.getPost(123L));
        verify(this.postRepository).findById((Long) any());
        assertTrue(this.postService.getAllPosts().isEmpty());
    }

    @Test
    void testGetPost2() {
        when(this.postRepository.findById((Long) any())).thenThrow(new RuntimeException("An error occurred"));
        assertThrows(RuntimeException.class, () -> this.postService.getPost(123L));
        verify(this.postRepository).findById((Long) any());
    }

    @Test
    void testGetPost3() {
        when(this.postRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> this.postService.getPost(123L));
        verify(this.postRepository).findById((Long) any());
    }

    @Test
    void testFind() {
        User user = new User();
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");

        Post post = new Post();
        post.setBody("Not all who wander are lost");
        post.setCreator(user);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        post.setDateCreated(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        post.setId(123L);
        post.setTitle("Dr");
        Optional<Post> ofResult = Optional.of(post);
        when(this.postRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(post, this.postService.find(123L));
        verify(this.postRepository).findById((Long) any());
        assertTrue(this.postService.getAllPosts().isEmpty());
    }

    @Test
    void testFind2() {
        when(this.postRepository.findById((Long) any())).thenThrow(new RuntimeException("An error occurred"));
        assertThrows(RuntimeException.class, () -> this.postService.find(123L));
        verify(this.postRepository).findById((Long) any());
    }

    @Test
    void testFind3() {
        when(this.postRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> this.postService.find(123L));
        verify(this.postRepository).findById((Long) any());
    }
}

