package com.example.everymutsa.utils;

import com.example.everymutsa.utils.redis.SearchRankingRedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions;

@SpringBootTest
@Slf4j
class RedisSearchUtilsTest {
    @Test
    void SearchKeywordTest() {
        //given
        String[] keywords = {
                "apple", "banana", "cherry", "date", "elderberry", "fig", "grape",
                "apple",
                "apple", "banana",
                "apple", "banana",
                "apple", "banana", "cherry", "date",
                "apple", "banana", "cherry", "date", "elderberry",
                "apple", "banana", "cherry", "date", "elderberry", "fig",
                "apple", "banana", "cherry", "date", "elderberry", "fig", "grape",
                "cherry","cherry", "cherry","cherry","cherry","cherry","cherry","cherry","cherry",
        };

        //when
        for(String keyword : keywords) SearchRankingRedisUtils.addSearchKeyword(keyword);

        //then
        log.info(SearchRankingRedisUtils.readTopSearchKeywords(3).toString());
        Assertions.assertEquals(SearchRankingRedisUtils.readTopSearchKeywords(3).get(0).getKeyword(), "cherry");

    }

}