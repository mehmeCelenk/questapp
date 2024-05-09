package com.app.quest.service;

import com.app.quest.exception.NotFoundException;
import com.app.quest.model.entity.Like;
import com.app.quest.model.entity.Post;
import com.app.quest.model.entity.User;
import com.app.quest.model.response.LikeResponse;
import com.app.quest.repository.LikeRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final DependenciesControl dependenciesControl;
    private final UserService userService;

    public LikeService(LikeRepository likeRepository, DependenciesControl dependenciesControl, UserService userService) {
        this.likeRepository = likeRepository;
        this.dependenciesControl = dependenciesControl;
        this.userService = userService;
    }


    public List<LikeResponse> getAllLikesWithParam(Optional<String> userId, Optional<Long> postId) {
        List<Like> list = userId.flatMap(u -> postId.map(p -> likeRepository.findByUserUUIDAndPostId(u, p)))
                .orElseGet(() -> userId.map(likeRepository::findByUserUUID)
                        .orElseGet(() -> postId.map(likeRepository::findByPostId)
                                .orElse(likeRepository.findAll())));

        return list.stream()
                .map(like -> new LikeResponse(like.getUser().getUUID(), like.getPost().getId()))
                .collect(Collectors.toList());
    }

    public LikeResponse getById(Long id){
        Like like = findById(id);
        return LikeResponse.convert(like);
    }

    public LikeResponse createLike(String userId, Long postId) {
        User user = userService.findById(userId);
        Post post = dependenciesControl.findPostById(postId);

        if(user != null && post != null) {
            Like like = new Like();
            like.setPost(post);
            like.setUser(user);

            likeRepository.save(like);
            return LikeResponse.convert(like);
        } else {
            throw new NotFoundException("User not found or Post not found");
        }
    }

    public void deleteLike(Long id){
        likeRepository.deleteById(id);
    }

    protected Like findById(Long id){
        return likeRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found like with id: " + id));
    }


}
