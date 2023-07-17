package com.example.webservice.service;

import com.example.webservice.entity.PostEntity;
import com.example.webservice.model.Post;
import com.example.webservice.repository.PostRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostService {
    private final RestTemplate restTemplate;
    private final PostDataService postDataService;

    @Value("${api.placeholder}")
    private String api;

    public PostService(RestTemplate restTemplate, PostRepository postRepository, PostDataService postDataService) {
        this.restTemplate = restTemplate;
        this.postDataService = postDataService;
    }

    public Post save(Post post) {
        HttpEntity<Post> entity = new HttpEntity<>(post, getHeaders());
        Post result = restTemplate.postForObject(this.api + "/posts", entity, Post.class);
        return result;
    }

    public Post update(Long id,Post post) {
        HttpEntity<Post> entity = new HttpEntity<>(post, getHeaders());
        Post result = restTemplate.exchange(this.api + "/posts"+id+"/comments",HttpMethod.PUT, entity, Post.class).getBody();
        return result;
    }


    public Object findAll() {
        HttpEntity<Post[]> entity = new HttpEntity<>(getHeaders());
        Post[] result = restTemplate.exchange(this.api + "/posts", HttpMethod.GET, entity, Post[].class).getBody();

        postDataService.saveAll(result);

        return result;
    }

    public List<Post> findAllByQueryParam(Long postId){
        HttpEntity<List<Post>> entity = new HttpEntity<>(getHeaders());
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(this.api+"/comments")
                .queryParam("postId","{postId}")
                .encode()
                .toUriString();
        Map<String, Object> params = new HashMap<>();
        params.put("postId",postId);
        List<Post> result = restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, List.class,params).getBody();
        return result;
    }

    public Page<PostEntity> findAll(Pageable pageable){
        return postDataService.findAll(pageable);
    }



    private HttpHeaders getHeaders(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return httpHeaders;
    }

}
