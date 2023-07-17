package com.example.webservice.service;

import com.example.webservice.entity.PostEntity;
import com.example.webservice.model.Post;
import com.example.webservice.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostDataService {

    final private PostRepository postRepository;


    public PostDataService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostEntity save(PostEntity post){
        return postRepository.save(post);
    }

    public Object saveAll(Post[] posts){

        List<PostEntity> postEntities = new ArrayList<>();
        for (Post post : posts) {
            PostEntity postEntity = new PostEntity();
            postEntity.setPostId(post.getId());
            postEntity.setUserId(post.getUserId());
            postEntity.setTitle(post.getTitle());
            postEntity.setBody(post.getBody());
            postEntities.add(postEntity);
        }

        return postRepository.saveAll(postEntities);
    }

    @Transactional(readOnly = true)
    public Page<PostEntity> findAll(Pageable pageable){
        return postRepository.findAll(pageable);
    }
}
