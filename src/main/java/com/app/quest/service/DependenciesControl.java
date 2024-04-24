package com.app.quest.service;

import com.app.quest.exception.NotFoundException;
import com.app.quest.model.entity.Post;
import com.app.quest.repository.LikeRepository;
import com.app.quest.repository.PostRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class DependenciesControl {
    private final PostRepository postRepository;

    public DependenciesControl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    protected Post findPostById(Long id){
        return postRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found post with id: " + id));
    }


}
