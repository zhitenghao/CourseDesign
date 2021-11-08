package com.swjt.community;

import com.swjt.community.entity.Article;
import com.swjt.community.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CommunicationPlatformApplicationTests {

    @Autowired
    ArticleService articleService;

    @Test
    void nihao(){
        List<Article> articles = articleService.listByCategory("16079fd48923558881177ab3e6beb60c");
        System.out.println("-----分割线-----");
        System.out.println(articles);
        System.out.println("-----分割线-----");
    }
    @Test
    void contextLoads() {
    }

}
