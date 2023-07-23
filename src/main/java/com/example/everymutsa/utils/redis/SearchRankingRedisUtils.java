package com.example.everymutsa.utils.redis;

import com.example.everymutsa.utils.redis.dto.CachingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class SearchRankingRedisUtils {
    private static RedisTemplate<String, String> redisSearchTemplate;
    private static final String rankingKey = "search_ranking";

    @Autowired
    private SearchRankingRedisUtils(@Qualifier("redisTemplate") RedisTemplate<String, String> redisSearchTemplate)  {
        SearchRankingRedisUtils.redisSearchTemplate=redisSearchTemplate;
    }

    public static void addSearchKeyword(String searchString) {
        for(String keyword : searchString.split(" "))
            redisSearchTemplate.opsForZSet().incrementScore(rankingKey, keyword, 1);
    }

    public static List<CachingResponse> readTopSearchKeywords(int count) {
        Set<ZSetOperations.TypedTuple<String>> result = redisSearchTemplate.opsForZSet().reverseRangeWithScores(rankingKey, 0, count - 1);

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
