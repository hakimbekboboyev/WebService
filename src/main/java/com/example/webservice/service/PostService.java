package com.example.webservice.service;

import com.example.webservice.model.Post;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PostService {
    private final RestTemplate restTemplate;

    public PostService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Post> findAll(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<List<Post>> entity = new HttpEntity<>(httpHeaders);
        List<Post> result = restTemplate.exchange("", HttpMethod.GET,entity,List.class).getBody();
    }
}
