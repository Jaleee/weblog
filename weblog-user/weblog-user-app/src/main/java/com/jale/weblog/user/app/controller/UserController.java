package com.jale.weblog.user.app.controller;

import com.alibaba.fastjson.JSON;
import com.jale.weblog.article.api.dataobject.Article;
import com.jale.weblog.article.api.service.ArticleService;
import com.jale.weblog.common.rocketmq.MQEnums;
import com.jale.weblog.common.rocketmq.MQProducer;
import com.jale.weblog.common.rocketmq.MQTags;
import com.jale.weblog.user.api.dataobject.User;
import com.jale.weblog.user.api.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Reference
    private UserService userService;

    @Reference
    private ArticleService articleService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/login")
    public String login(@RequestBody User user) throws Exception {
        User loginUser = userService.login(user);

        if (loginUser == null) {
            throw new Exception("账号或密码错误");
        }

        return "登陆成功";
    }

    @GetMapping("/redisSave")
    public String redisSave() throws Exception {
        redisTemplate.opsForValue().set("hello", "jale");

        return redisTemplate.opsForValue().get("hello").toString();
    }

    @PostMapping("/getUserArticle")
    public String getUserArticle(@RequestBody Article article) {
        List<Article> articles = articleService.selectList(article);

        return JSON.toJSONString(articles);
    }

    @GetMapping("/testmq")
    public boolean testmq() {
        Long body = new Random().nextLong();
        boolean b = MQProducer.sendSync(MQEnums.TOPIC.getValue(), MQTags.ORDER_TAG, body.toString(), "下单-" + body.toString());

        return b;
    }

}
