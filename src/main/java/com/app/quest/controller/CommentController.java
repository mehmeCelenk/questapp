package com.app.quest.controller;

import com.app.quest.model.request.CommentCreateRequest;
import com.app.quest.model.request.CommentUpdateRequst;
import com.app.quest.model.response.CommentResponse;
import com.app.quest.service.CommentService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/questapp/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}/getall")
    public ResponseEntity<List<CommentResponse>> getAll(@RequestParam Optional<String> userId, @RequestParam Optional<Long> postId){
        return ResponseEntity.ok(commentService.getAll(userId,postId));
    }

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<CommentResponse> createComent(@Valid @RequestBody CommentCreateRequest commentCreateRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(commentCreateRequest));
    }

    @PutMapping("/update")
    @Transactional
    public ResponseEntity<CommentResponse> updateComment(@Valid @RequestBody CommentUpdateRequst commentUpdateRequst){
        return ResponseEntity.ok(commentService.updateComment(commentUpdateRequst));
    }

    @DeleteMapping("/{id}")
    public void deleteComment(Long id){
        commentService.deleteComment(id);
    }
}
