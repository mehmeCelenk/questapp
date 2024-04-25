package com.app.quest.controller;

import com.app.quest.model.entity.Post;
import com.app.quest.model.request.PostCreateRequest;
import com.app.quest.model.request.PostUpdateRequest;
import com.app.quest.model.response.PostResponse;
import com.app.quest.service.LikeService;
import com.app.quest.service.PostService;
import com.app.quest.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/questapp/post")
public class PostController {
    private final PostService postService;


    public PostController(PostService postService) {
        this.postService = postService;

    }

    @GetMapping("{id}")
    public ResponseEntity<List<PostResponse>> getAllPosts(Optional<String> id) {
        return ResponseEntity.ok(postService.getAll(id));
    }


    @PostMapping
    @Transactional
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostCreateRequest createRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(createRequest));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<PostResponse> updatePost(@Valid @RequestBody PostUpdateRequest updateRequest) {
        return ResponseEntity.ok(postService.UpdatePost(updateRequest));
    }

    @DeleteMapping("{id}")
    public void deletePostById(@PathVariable Long id) {
        postService.deletePost(id);
    }
}
