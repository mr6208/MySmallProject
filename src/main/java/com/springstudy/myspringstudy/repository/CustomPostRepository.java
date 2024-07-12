package com.springstudy.myspringstudy.repository;

import com.springstudy.myspringstudy.domain.Post;
import com.springstudy.myspringstudy.dto.request.PostSearch;

import java.util.List;

public interface CustomPostRepository {
    List<Post> getList(PostSearch postSearch);
}
