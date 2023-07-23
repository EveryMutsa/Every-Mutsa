package com.example.everymutsa.utils.redis.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class CachingResponse {
    private Integer ranking;
    private Object keyword;
    private Double score;

    @Builder
    public static CachingResponse builder(Integer ranking, Object keyword, Double score) {
        CachingResponse response = new CachingResponse();
        response.ranking = ranking;
        response.keyword = keyword;
        response.score = score;
        return response;
    }
}
