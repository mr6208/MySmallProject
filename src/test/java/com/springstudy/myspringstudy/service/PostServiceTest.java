package com.springstudy.myspringstudy.service;

import com.springstudy.myspringstudy.domain.Post;
import com.springstudy.myspringstudy.dto.request.PostCreate;
import com.springstudy.myspringstudy.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        Post post = postService.get(savePost.getId());

        // then
        assertNotNull(post);
        assertEquals(1L, postRepository.count());
        assertEquals("ㅎㅇ", post.getTitle());
        assertEquals("ㅎㅇㅎㅇ", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test4() throws Exception {
        // given
        Post post = Post.builder()
                .title("ㅎㅇㅎㅇ")
                .content("ㅎㅇ")
                .build();

        postRepository.save(post);

        // expected
        mockMvc.perform(get("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("ㅎㅇㅎㅇ"))
                .andExpect(jsonPath("$.content").value("ㅎㅇ"))
                .andDo(print());

    }
}