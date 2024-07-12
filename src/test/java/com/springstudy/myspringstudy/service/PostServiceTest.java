package com.springstudy.myspringstudy.service;

import com.springstudy.myspringstudy.domain.Post;
import com.springstudy.myspringstudy.dto.request.PostCreate;
import com.springstudy.myspringstudy.dto.request.PostSearch;
import com.springstudy.myspringstudy.dto.response.PostResponse;
import com.springstudy.myspringstudy.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성이 잘 되는지")
    void test1() {
        // given
        PostCreate request = PostCreate.builder()
                .title("제목목")
                .content("내용용")
                .build();
        //when
        postService.write(request);

        //then
        assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("제목목", post.getTitle());
        assertEquals("내용용", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test2() {
        // given
        Post savePost = Post.builder()
                .title("ㅎㅇ")
                .content("ㅎㅇㅎㅇ")
                .build();
        postRepository.save(savePost);

        // when
        PostResponse postResponse = postService.get(savePost.getId());

        // then
        assertNotNull(postResponse);
        assertEquals(1L, postRepository.count());
        assertEquals("ㅎㅇ", postResponse.getTitle());
        assertEquals("ㅎㅇㅎㅇ", postResponse.getContent());
    }

    @Test
    @DisplayName("글 1페이지 조회")
    void test3() {
        // given
        List<Post> requestPost = IntStream.range(0, 20)
                .mapToObj(i -> {
                    return Post.builder()
                            .title("제목입니다 " + i)
                            .content("내용입니다 " + i)
                            .build();
                })
                .collect(Collectors.toList());

        postRepository.saveAll(requestPost);

        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .size(10)
                .build();

        // when
        List<PostResponse> posts = postService.findAll(postSearch);

        // then
        assertEquals(10L, posts.size());
        assertEquals("제목입니다 19", posts.get(0).getTitle());
    }
}