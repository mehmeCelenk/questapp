package com.app.quest.service;

import com.app.quest.exception.NotFoundException;
import com.app.quest.model.entity.Comment;
import com.app.quest.model.entity.Post;
import com.app.quest.model.entity.User;
import com.app.quest.model.request.CommentCreateRequest;
import com.app.quest.model.request.CommentUpdateRequst;
import com.app.quest.model.response.CommentResponse;
import com.app.quest.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final DependenciesControl   dependenciesControl;

    public CommentService(CommentRepository commentRepository, UserService userService, DependenciesControl dependenciesControl) {
        this.commentRepository = commentRepository;
        this.userService       = userService;
        this.dependenciesControl = dependenciesControl;
    }

    public List<CommentResponse> getAll(Optional<String> userId, Optional<Long> postId) {
        List<Comment> commentList = userId.flatMap(u -> postId.map(p -> commentRepository.findByUserUUIDAndPostId(u, p)))
                .orElseGet(() -> userId.map(commentRepository::findByUserUUID)
                        .orElseGet(() -> postId.map(commentRepository::findByPostId)
                                .orElse(commentRepository.findAll())));

        return commentList.stream()
                .map(comment -> new CommentResponse(comment.getText(), comment.getUser().getUUID(), comment.getPost().getId()))
                .collect(Collectors.toList());
    }

    public CommentResponse createComment(CommentCreateRequest commentCreateRequest){
        User user = userService.findById(commentCreateRequest.userId());
        Post post = dependenciesControl.findPostById(commentCreateRequest.postId());

        if(user != null && post != null){
            Comment comment = new Comment();

            comment.setText(commentCreateRequest.text());
            comment.setUser(user);
            comment.setPost(post);

            commentRepository.save(comment);

            return CommentResponse.convert(comment);
        }

         throw new NotFoundException("Comment not found or Post not found, or User Not found");

    }

    public CommentResponse updateComment(CommentUpdateRequst commentUpdateRequst){
        Comment comment = findByid(commentUpdateRequst.commentId());

        if(comment != null){
            comment.setText(commentUpdateRequst.text());

            commentRepository.save(comment);
            return CommentResponse.convert(comment);
        }

        throw new NotFoundException("Comment not found");
    }

    public void deleteComment(Long id){
        Comment comment = findByid(id);

        commentRepository.deleteById(id);
    }

    private Comment findByid(Long commentId){
        return commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Not found comment with id: " + commentId));
    }

}
