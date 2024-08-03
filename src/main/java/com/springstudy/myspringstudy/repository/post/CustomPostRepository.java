package com.springstudy.myspringstudy.repository.post;

import com.springstudy.myspringstudy.domain.post.Post;
import com.springstudy.myspringstudy.dto.post.request.PostSearch;

import java.util.List;

public interface CustomPostRepository {
    List<Post> getList(PostSearch postSearch);
}
