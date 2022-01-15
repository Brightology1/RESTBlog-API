package com.example.restblogapp.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.restblogapp.entities.Comment;
import com.example.restblogapp.entities.Post;
import com.example.restblogapp.entities.User;
import com.example.restblogapp.repositories.CommentRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CommentService.class})
@ExtendWith(SpringExtension.class)
class CommentServiceTest {
    @MockBean
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    @Test
    void testGetComments() {
        ArrayList<Comment> commentList = new ArrayList<>();
        when(this.commentRepository.findByPostId((Long) any())).thenReturn(commentList);
        List<Comment> actualComments = this.commentService.getComments(123L);
        assertSame(commentList, actualComments);
        assertTrue(actualComments.isEmpty());
        verify(this.commentRepository).findByPostId((Long) any());
    }

    @Test
    void testComment() {
        User user = new User();
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");

        User user1 = new User();
        user1.setPassword("iloveyou");
        user1.setRoles(new ArrayList<>());
        user1.setUsername("janedoe");

        Post post = new Post();
        post.setBody("Not all who wander are lost");
        post.setCreator(user1);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        post.setDateCreated(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        post.setId(123L);
        post.setTitle("Dr");

        Comment comment = new Comment();
        comment.setCreator(user);
        comment.setId(123L);
        comment.setPost(post);
        comment.setText("Text");
        when(this.commentRepository.save((Comment) any())).thenReturn(comment);

        User user2 = new User();
        user2.setPassword("iloveyou");
        user2.setRoles(new ArrayList<>());
        user2.setUsername("janedoe");

        User user3 = new User();
        user3.setPassword("iloveyou");
        user3.setRoles(new ArrayList<>());
        user3.setUsername("janedoe");

        Post post1 = new Post();
        post1.setBody("Not all who wander are lost");
        post1.setCreator(user3);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        post1.setDateCreated(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        post1.setId(123L);
        post1.setTitle("Dr");

        Comment comment1 = new Comment();
        comment1.setCreator(user2);
        comment1.setId(123L);
        comment1.setPost(post1);
        comment1.setText("Text");
        this.commentService.comment(comment1);
        verify(this.commentRepository).save((Comment) any());
    }

    @Test
    void testDeletePost() {
        doNothing().when(this.commentRepository).deleteById((Long) any());
        assertTrue(this.commentService.deletePost(123L));
        verify(this.commentRepository).deleteById((Long) any());
    }
}

