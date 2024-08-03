package com.springstudy.myspringstudy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springstudy.myspringstudy.domain.post.Post;
import com.springstudy.myspringstudy.dto.post.request.PostCreate;
import com.springstudy.myspringstudy.repository.post.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureRestDocs(uriHost = "api.mr6208.com")
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureMockMvc
public class PostControllerDocTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Test
    @DisplayName("글 단건조회")
    void test1() throws Exception {

        // given
        Post post = Post.builder()
                .title("제목이다")
                .content("내용이다")
                .build();

        postRepository.save(post);

        // expected
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/post/{postId}", 1L)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("post-get", pathParameters(
                                parameterWithName("postId").description("게시글 ID")
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("id").description("게시글 ID"),
                                PayloadDocumentation.fieldWithPath("title").description("게시글의 제목"),
                                PayloadDocumentation.fieldWithPath("content").description("게시글의 내용")
                        )
                ));

    }

    @Test
    @DisplayName("글 등록")
    void test2() throws Exception {

        //given
        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);


        // expected
        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/post")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("post-create",
                        PayloadDocumentation.requestFields(
                                PayloadDocumentation.fieldWithPath("title").description("게시글 제목")
                                        .attributes(Attributes.key("constraint").value("제목을 입력하세요")),
                                PayloadDocumentation.fieldWithPath("content").description("게시글 내용").optional()

                        )
                ));

    }
}
