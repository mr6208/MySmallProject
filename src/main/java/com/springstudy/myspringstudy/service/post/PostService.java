package com.springstudy.myspringstudy.service.post;

import com.springstudy.myspringstudy.domain.post.Post;
import com.springstudy.myspringstudy.dto.post.request.PostCreate;
import com.springstudy.myspringstudy.dto.post.request.PostSearch;
import com.springstudy.myspringstudy.dto.post.request.PostUpdate;
import com.springstudy.myspringstudy.dto.post.response.PostResponse;
import com.springstudy.myspringstudy.exception.post.PostNotFound;
import com.springstudy.myspringstudy.repository.post.PostRepository;
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
//                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 글 입니다."))
                .orElseThrow(PostNotFound::new);

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
        Post post = postRepository.findById(id)
//                .orElseThrow(() ->new IllegalArgumentException("존재하는 글 입니다."));
                .orElseThrow(PostNotFound::new);

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

    public void delete(Long id) {
        Post post = postRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
                .orElseThrow(PostNotFound::new);

        postRepository.delete(post);
    }
}
