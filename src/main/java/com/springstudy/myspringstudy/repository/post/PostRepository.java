package com.springstudy.myspringstudy.repository.post;


import com.springstudy.myspringstudy.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, CustomPostRepository {
}
