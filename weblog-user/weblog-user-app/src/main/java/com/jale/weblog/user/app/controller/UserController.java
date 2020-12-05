package com.jale.weblog.user.app.controller;

import com.alibaba.fastjson.JSON;
import com.jale.weblog.article.api.dataobject.Article;
import com.jale.weblog.article.api.service.ArticleService;
import com.jale.weblog.common.annotations.IgnoreAuth;
import com.jale.weblog.common.exception.WeblogException;
import com.jale.weblog.common.rocketmq.MQEnums;
import com.jale.weblog.common.rocketmq.MQTags;
import com.jale.weblog.common.commonobjects.R;
import com.jale.weblog.user.api.dataobject.User;
import com.jale.weblog.user.api.service.UserService;
import com.jale.weblog.user.service.mq.MQTxProducer;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Reference
    private UserService userService;

    @Reference
    private ArticleService articleService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/hello")
    public String hello() {
        throw new WeblogException("抛出自定义异常");
        //return "hello";
    }

    @IgnoreAuth
    @GetMapping("/helloNoAuth")
    public R helloNoAuth() {

        return R.success();
    }

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
        Long body = System.currentTimeMillis();
        //boolean b = MQProducer.sendSync(MQEnums.TOPIC.getValue(), MQTags.ORDER_TAG, body.toString(), "下单-" + body.toString());
        boolean b = MQTxProducer.sendTx(MQEnums.TX_TOPIC.getValue(), MQTags.ORDER_TAG, body.toString(), body.toString());

        return b;
    }

    @GetMapping("/testLog")
    public void testLog() {
        System.out.println("日志级别信息:");
        System.out.println("trace:" + LOGGER.isTraceEnabled());
        System.out.println("debug:" + LOGGER.isDebugEnabled());
        System.out.println("info:" + LOGGER.isInfoEnabled());
        System.out.println("warn:" + LOGGER.isWarnEnabled());
        System.out.println("error:" + LOGGER.isErrorEnabled());
        LOGGER.trace("这是trace日志");
        LOGGER.debug("这是debug日志");
        LOGGER.info("这是info日志");
        LOGGER.warn("这是warn日志");
        LOGGER.error("这是error日志");
    }

    @GetMapping("/testUser")
    public R testUser() {
        User user = new User();
        user.setUserName("sun");
        user.setPassword("123");

        return R.success(user);
    }

    @GetMapping("/testServiceException")
    public R testServiceException() {
        userService.testServiceException();
        return R.success();
    }

}
