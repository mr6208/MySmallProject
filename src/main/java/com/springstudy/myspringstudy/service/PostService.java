package com.springstudy.myspringstudy.service;

import com.springstudy.myspringstudy.domain.Post;
import com.springstudy.myspringstudy.dto.request.PostCreate;
import com.springstudy.myspringstudy.dto.request.PostSearch;
import com.springstudy.myspringstudy.dto.request.PostUpdate;
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

    public PostResponse update(Long id, PostUpdate postUpdate) {
        Post post = postRepository.findById(id).
                orElseThrow(() ->new IllegalArgumentException("존재하는 글 입니다."));

//        PostEditor.PostEditorBuilder builder = post.toEditor();

//        PostEditor postEditor = builder.title(postUpdate.getTitle())
//                .content(postUpdate.getContent())
//                .build();

        post.update(postUpdate.getContent(), postUpdate.getTitle());

        return PostResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .title(post.getTitle())
                .build();
    }
}
