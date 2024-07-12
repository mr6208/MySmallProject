package com.springstudy.myspringstudy.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static java.lang.Math.max;

@Getter
@Setter
@Builder
public class PostSearch {
    private static final int MAX_SIZE = 2000;
    @Builder.Default
    private int page = 1;
    @Builder.Default
    private int size = 10;

    public long getOffset() {
        return (long) (max(1,page) - 1) * Math.min(size, MAX_SIZE);
    }
}
