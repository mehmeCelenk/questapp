package com.app.quest.service;

import com.app.quest.exception.NotFoundException;
import com.app.quest.model.entity.Post;
import com.app.quest.model.entity.User;
import com.app.quest.model.request.PostCreateRequest;
import com.app.quest.model.request.PostUpdateRequest;
import com.app.quest.model.response.LikeResponse;
import com.app.quest.model.response.PostResponse;
import com.app.quest.repository.PostRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final LikeService likeService;


    public PostService(PostRepository postRepository, UserService userService, LikeService likeService) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.likeService = likeService;
    }


    public List<PostResponse> getAll(Optional<String> userId){
        List<Post> list;
        if(userId.isPresent()){
            list = this.postRepository.findByUserUUID(userId.get());
        }else {
            list = this.postRepository.findAll();
        }
        return list.stream().map(post -> {
            List<LikeResponse> likes = likeService.getAllLikesWithParam(Optional.ofNullable(null), Optional.of(post.getId()));
            return PostResponse.convert(post,likes);}).collect(Collectors.toList());
    }


    public PostResponse createPost(PostCreateRequest createRequest){
        User user = userService.findById(createRequest.userId());

        Post post = new Post();

        post.setText(createRequest.text());
        post.setTitle(createRequest.title());
        post.setUser(user);

        post = postRepository.save(post);

        return PostResponse.convert(post, null);
    }

    public PostResponse UpdatePost(PostUpdateRequest updateRequest){
        Post post = findPostById(updateRequest.id());

        if (post != null) {
            post.setText(updateRequest.text());
            post.setTitle(updateRequest.title());

            post = postRepository.save(post);
            return PostResponse.convert(post, null);
        }
        throw new NotFoundException("Post not found");
    }

    public void deletePost(Long id){
        Post post = findPostById(id);
        postRepository.deleteById(id);

    }

    protected Post findPostById(Long id){
        return postRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found post with id: " + id));
    }
}
