package com.springstudy.myspringstudy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springstudy.myspringstudy.domain.Post;
import com.springstudy.myspringstudy.dto.request.PostCreate;
import com.springstudy.myspringstudy.dto.request.PostUpdate;
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

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/post 요청시 asdf를 출력하는지")
    void test() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
                .title("1234567890")
                .content("내용용")
                .build();

        String json = objectMapper.writeValueAsString(request);

        System.out.println(json);

        // then
        mockMvc.perform(post("/post")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("/posts 요청시 title값은 필수여야한다.")
    void test2() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
                .title(null)
                .content("내용용")
                .build();

        String json = objectMapper.writeValueAsString(request);

        System.out.println(json);
        mockMvc.perform(post("/post")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("제목을 입력해주세용"))
                .andDo(print());

    }

//    @Test
//    @DisplayName("/posts 요청시 title값은 10자 이상이여야한다.")
//    void testTitle() throws Exception {
//        //given
//        PostCreate request = PostCreate.builder()
//                .title("123456789")
//                .content("내용용")
//                .build();
//
//        String json = objectMapper.writeValueAsString(request);
//
//        System.out.println(json);
//        mockMvc.perform(post("/post")
//                        .contentType(APPLICATION_JSON)
//                        .content(json)
//                )
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.code").value("400"))
//                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
//                .andExpect(jsonPath("$.validation.title").value("제목은 최소 10자 이상입니다"))
//                .andDo(print());
//
//    }

    @Test
    @DisplayName("/posts 요청시 DB에 값이 저장된다.")
    void test3() throws Exception {

        //given
        PostCreate request = PostCreate.builder()
                .title("1234567890")
                .content("내용용")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/post")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
        // then
        assertEquals(1L, postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("1234567890", post.getTitle());
        assertEquals("내용용", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test4() throws Exception {
        // given
        Post post = Post.builder()
                .title("123456789012345")
                .content("ㅎㅇ")
                .build();

        // 서버에서 제목을 최대 10글자만 반환하도록 해주세요
        postRepository.save(post);

        // expected
        mockMvc.perform(get("/post/{postId}", post.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("1234567890"))
                .andExpect(jsonPath("$.content").value("ㅎㅇ"))
                .andDo(print());

    }

    @Test
    @DisplayName("페이지를 0으로 요청해도 첫 페이지를 가져온다")
    void test5() throws Exception {
        // given
        List<Post> requestPost = IntStream.range(1, 31)
                .mapToObj(i -> {
                    return Post.builder()
                            .title("제목입니다 " + i)
                            .content("내용입니다 " + i)
                            .build();
                })
                .collect(Collectors.toList());

        postRepository.saveAll(requestPost);

        // expected
        mockMvc.perform(get("/posts?page=0&size=10")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(10)))
                .andExpect(jsonPath("$[0].id").value(requestPost.get(29).getId()))
                .andExpect(jsonPath("$[0].title").value("제목입니다 30"))
                .andExpect(jsonPath("$[0].content").value("내용입니다 30"))
                .andDo(print());

    }

    @Test
    @DisplayName("글 제목 수정")
    void test6() throws Exception {
        // given
        Post post = Post.builder()
                .title("제목이다")
                .content("내용이다")
                .build();

        postRepository.save(post);

        PostUpdate updatePost = PostUpdate.builder()
                .title("바뀐 제목이다")
                .content("내용이다")
                .build();

        // expected
        mockMvc.perform(patch("/post/{postId}", post.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePost)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("바뀐 제목이다"))
                .andExpect(jsonPath("$.content").value("내용이다"))
                .andDo(print());
    }

    @Test
    @DisplayName("글 삭제 요청")
    void test7() throws Exception {
        // given
        Post post = Post.builder()
                .title("제목이다")
                .content("내용이다")
                .build();

        postRepository.save(post);

        // expected
        mockMvc.perform(delete("/post/{postId}", post.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 게시글 조회")
    void test9() throws Exception {
        // expected
        mockMvc.perform(delete("/post/{postId}", 1L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 게시글 수정")
    void test10() throws Exception {

        // given
        PostUpdate updatePost = PostUpdate.builder()
                .title("바뀐 제목이다")
                .content("내용이다")
                .build();

        // expected
        mockMvc.perform(patch("/post/{postId}", 1L, updatePost)
                        .content(objectMapper.writeValueAsString(updatePost))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 작성시 제목에 '나쁜말'이 있으면 안된다.")
    void test11() throws Exception {

        //given
        PostCreate request = PostCreate.builder()
                .title("나쁜말 진짜 힘든 하루였다")
                .content("진짜루")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/post")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}