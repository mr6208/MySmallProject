package com.springstudy.myspringstudy.repository.post;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.springstudy.myspringstudy.domain.post.Post;
import com.springstudy.myspringstudy.dto.post.request.PostSearch;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.springstudy.myspringstudy.domain.post.QPost.post;


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
