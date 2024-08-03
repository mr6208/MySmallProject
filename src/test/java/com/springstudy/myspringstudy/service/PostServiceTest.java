package com.springstudy.myspringstudy.service;

import com.springstudy.myspringstudy.domain.post.Post;
import com.springstudy.myspringstudy.dto.post.request.PostCreate;
import com.springstudy.myspringstudy.dto.post.request.PostSearch;
import com.springstudy.myspringstudy.dto.post.request.PostUpdate;
import com.springstudy.myspringstudy.dto.post.response.PostResponse;
import com.springstudy.myspringstudy.exception.post.PostNotFound;
import com.springstudy.myspringstudy.repository.post.PostRepository;
import com.springstudy.myspringstudy.service.post.PostService;
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

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    @DisplayName("글 제목 수정")
    void test4() {
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

        // when
        postService.update(post.getId(), updatePost);

        // then
        Post updatedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 글 입니다."));
        assertEquals("바뀐 제목이다", updatedPost.getTitle());
        assertEquals("내용이다", updatedPost.getContent());
    }

    @Test
    @DisplayName("글 내용 수정")
    void test5() {
        // given
        Post post = Post.builder()
                .title("제목이다")
                .content("내용이다")
                .build();

        postRepository.save(post);

        PostUpdate updatePost = PostUpdate.builder()
                .title("제목이다")
                .content("바뀐 내용이다")
                .build();

        // when
        postService.update(post.getId(), updatePost);

        // then
        Post updatedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 글 입니다."));
        assertEquals("제목이다", updatedPost.getTitle());
        assertEquals("바뀐 내용이다", updatedPost.getContent());
    }

    @Test
    @DisplayName("게시글 삭제")
    void test6() {
        // given
        Post post = Post.builder()
                .title("제목이다")
                .content("내용이다")
                .build();

        postRepository.save(post);

        // when
        postService.delete(post.getId());

        // then
        assertEquals(0, postRepository.count());
    }

    @Test
    @DisplayName("존재하지 않는 글을 조회할시 에러 반환")
    void test7() {
        // given
        Post post = Post.builder()
                .title("제목이다")
                .content("내용이다")
                .build();

        postRepository.save(post);

        // expected
        assertThrows(PostNotFound.class, () -> {
            postService.get(post.getId() + 1L);
        });
    }

    @Test
    @DisplayName("존재하지 않는 글을 삭제할시 예외 반환")
    void test8() {
        // given
        Post post = Post.builder()
                .title("제목이다")
                .content("내용이다")
                .build();

        postRepository.save(post);

        // expected
        assertThrows(PostNotFound.class, () -> {
            postService.delete(post.getId() + 1L);
        });
    }

    @Test
    @DisplayName("존재하지 않는 글을 수정할시 예외 반환")
    void test9() {
        // given
        Post post = Post.builder()
                .title("제목이다")
                .content("내용이다")
                .build();

        postRepository.save(post);

        PostUpdate update = PostUpdate.builder()
                .title("바뀐거 제목")
                .content("바뀐거 내용")
                .build();

        // expected
        assertThrows(PostNotFound.class, () -> {
            postService.update(post.getId() + 1L, update);
        });
    }
}