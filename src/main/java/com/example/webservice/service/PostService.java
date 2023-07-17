package com.example.webservice.service;

import com.example.webservice.model.Post;
import com.example.webservice.repository.PostRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

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

    public Object findAll(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Post[]> entity = new HttpEntity<>(httpHeaders);
        Post[] result = restTemplate.exchange(this.api+"/posts", HttpMethod.GET,entity,Post[].class).getBody();

        postDataService.saveAll(result);

        return result;
    }

}
