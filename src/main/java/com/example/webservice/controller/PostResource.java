package com.example.webservice.controller;

import com.example.webservice.entity.PostEntity;
import com.example.webservice.model.Post;
import com.example.webservice.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostResource {

    private final PostService postService;

    public PostResource(PostService postService) {
        this.postService = postService;
    }
    @PostMapping("/posts")
    public ResponseEntity creat(@RequestBody Post post){
        Post result = postService.save(post);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/posts")
    public ResponseEntity getAll(){
        Object result = postService.findAll();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/posts/params")
    public ResponseEntity getAllByParam(@RequestParam Long postId){
        List<Post> result = postService.findAllByQueryParam(postId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity update(@RequestBody Post post,@PathVariable Long id){
        Post result = postService.update(id, post);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/posts/paging")
    public ResponseEntity getAllByPaging(Pageable pageable){
        Page<PostEntity> result = postService.findAll(pageable);
        return ResponseEntity.ok(result);
    }



}
