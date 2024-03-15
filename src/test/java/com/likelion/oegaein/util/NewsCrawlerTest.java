package com.likelion.oegaein.util;

import com.likelion.oegaein.domain.news.util.NewsCrawler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NewsCrawlerTest {
    @Autowired
    private NewsCrawler newsCrawler;

    @Test
    public void test_crawler(){
        newsCrawler.newsCrawling();
    }
}
