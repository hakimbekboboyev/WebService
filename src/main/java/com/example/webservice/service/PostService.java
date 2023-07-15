package com.example.webservice.service;

import com.example.webservice.entity.PostEntity;
import com.example.webservice.model.Post;
import com.example.webservice.repository.PostRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class PostService {
    private final RestTemplate restTemplate;
    private final PostRepository postRepository;

    @Value("${api.placeholder}")
    private String api;

    public PostService(RestTemplate restTemplate, PostRepository postRepository) {
        this.restTemplate = restTemplate;
        this.postRepository = postRepository;
    }

    public List<PostEntity> findAll(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<List<Post>> entity = new HttpEntity<>(httpHeaders);
        List<Post> result = restTemplate.exchange(this.api+"/posts", HttpMethod.GET,entity,List.class).getBody();

        PostEntity postEntity = new PostEntity();
        for (Post post : result) {
            postEntity.setId(Math.toIntExact(post.getId()));
            postEntity.setUserId(post.getUserId());
            postEntity.setTitle(post.getTitle());
            postEntity.setBody(post.getBody());
            postRepository.save(postEntity);

        }
        return postRepository.findAll();
    }




}
