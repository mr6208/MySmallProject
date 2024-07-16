package com.springstudy.myspringstudy.controller;

import com.springstudy.myspringstudy.dto.request.PostCreate;
import com.springstudy.myspringstudy.dto.request.PostSearch;
import com.springstudy.myspringstudy.dto.request.PostUpdate;
import com.springstudy.myspringstudy.dto.response.PostResponse;
import com.springstudy.myspringstudy.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public void post(@RequestBody @Valid PostCreate request) throws Exception {
        request.validate();
        postService.write(request);
    }

    @GetMapping("/post/{postId}")
    public PostResponse get(@PathVariable("postId") Long id) {
        return postService.get(id);
    }

    @GetMapping("/posts")
    public List<PostResponse> getPosts(@ModelAttribute PostSearch postSearch) {
        return postService.findAll(postSearch);
    }
    @PatchMapping("/post/{postId}")
    public PostResponse update(@PathVariable("postId") Long id, @RequestBody @Valid PostUpdate postUpdate) {
        return postService.update(id, postUpdate);
    }

    @DeleteMapping("/post/{postId}")
    public void delete(@PathVariable("postId") Long id) {
        postService.delete(id);
    }
}
