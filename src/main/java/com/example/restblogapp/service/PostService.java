package com.example.restblogapp.service;

import com.example.restblogapp.entities.Post;
import com.example.restblogapp.entities.User;
import com.example.restblogapp.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    public void insert(Post post) {
        postRepository.save(post);
    }

    public List<Post> findByUser(User user){
        return postRepository.findByCreatorId(user.getId());
    }

    public boolean deletePost(Long postId){
        Post thePost = this.getPost(postId);
        if(thePost == null)
            return false;
        postRepository.deleteById(postId);
        return true;
    }

    public Post getPost(Long id) {
        var postOptional = postRepository.findById(id);
        if (postOptional.isPresent()){
            return postOptional.get();
        }
        else {
            throw new RuntimeException("id does not exist");
        }
    }

    public Post find(Long postId) {return this.getPost(postId);}
}
