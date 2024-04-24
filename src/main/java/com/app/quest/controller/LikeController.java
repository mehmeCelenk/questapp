package com.app.quest.controller;

import com.app.quest.model.request.LikeCreateRequest;
import com.app.quest.model.response.LikeResponse;
import com.app.quest.service.LikeService;
import com.app.quest.service.PostService;
import com.app.quest.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/questapp/like")
public class LikeController {
    @Lazy
    private final LikeService likeService;


    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping
    public ResponseEntity<List<LikeResponse>> getAllLikes(@RequestParam Optional<String> userId, @RequestParam Optional<Long> postId) {
        return ResponseEntity.ok(likeService.getAllLikesWithParam(userId, postId));
    }

    @GetMapping("{id}")
    public ResponseEntity<LikeResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(likeService.getById(id));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<LikeResponse> createLike(@RequestBody LikeCreateRequest createRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(likeService.createLike(createRequest.userId(), createRequest.postId()));
    }

    @DeleteMapping("{id}")
    public void deleteLike(@PathVariable long id) {
        likeService.deleteLike(id);
    }


}
