package com.springstudy.myspringstudy.service;

import com.springstudy.myspringstudy.domain.Post;
import com.springstudy.myspringstudy.dto.request.PostCreate;
import com.springstudy.myspringstudy.dto.request.PostSearch;
import com.springstudy.myspringstudy.dto.response.PostResponse;
import com.springstudy.myspringstudy.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    public void write(PostCreate request) {
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        postRepository.save(post);
    }

    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("글이 존재하지 않습니다."));

        return PostResponse.builder()
                .title(post.getTitle())
                .id(post.getId())
                .content(post.getContent())
                .build();
    }

    public List<PostResponse> findAll(PostSearch postSearch) {
        return postRepository.getList(postSearch).stream().map(PostResponse::new)
                .collect(Collectors.toList());
    }
}
