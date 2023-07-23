package com.example.everymutsa.utils.redis;

import com.example.everymutsa.utils.redis.dto.CachingResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class ViewCountRedisUtils {
    private static RedisTemplate<String, String> redisTemplate;
    private static final String rankingKey = "view_count";
    @Autowired
    private ViewCountRedisUtils(@Qualifier("viewCountRedisTemplate") RedisTemplate<String, String> redisViewCountTemplate) {
        ViewCountRedisUtils.redisTemplate = redisViewCountTemplate;
    }

    public static void addViewCount(Long postId) {
        redisTemplate.opsForZSet().incrementScore(rankingKey, String.valueOf(postId), 1);
    }

    public static List<CachingResponse> readTopViewCount(int count) {
        Set<ZSetOperations.TypedTuple<String>> result = redisTemplate.opsForZSet().reverseRangeWithScores(rankingKey, 0, count - 1);

        List<CachingResponse> topKeywords = new ArrayList<>();
        for (ZSetOperations.TypedTuple<String> tuple : result) {
            topKeywords.add(CachingResponse.builder(
                    topKeywords.size() + 1,
                    tuple.getValue(),
                    tuple.getScore()
            ));
        }
        return topKeywords;
    }

}
