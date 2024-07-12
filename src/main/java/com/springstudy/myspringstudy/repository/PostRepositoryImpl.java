package com.springstudy.myspringstudy.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.springstudy.myspringstudy.domain.Post;
import com.springstudy.myspringstudy.dto.request.PostSearch;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.springstudy.myspringstudy.domain.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements CustomPostRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getList(PostSearch postSearch) {
        return jpaQueryFactory.selectFrom(post)
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset())
                .orderBy(post.id.desc())
                .fetch();
    }
}
