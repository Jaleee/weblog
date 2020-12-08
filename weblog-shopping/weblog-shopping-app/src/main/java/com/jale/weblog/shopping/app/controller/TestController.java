package com.jale.weblog.shopping.app.controller;

import com.jale.weblog.common.commonobjects.R;
import com.jale.weblog.shopping.api.service.GoodsService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Reference
    private GoodsService goodsService;

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @GetMapping("/asyncTask")
    public R asyncTask() {
        goodsService.testAsyncTask();
        return R.success();
    }

    @GetMapping("/sendEmail")
    public R sendEmail() {
        // 普通邮件
        SimpleMailMessage mailMsg = new SimpleMailMessage();
        mailMsg.setSubject("来自JavaMailSender的自定义邮件");
        mailMsg.setText("测试发送邮件");
        mailMsg.setTo("1274684480@qq.com");
        mailMsg.setFrom("1274684480@qq.com");
        javaMailSender.send(mailMsg);

        return R.success();
    }

    @GetMapping("/sendMimeEmail")
    public R sendMimeEmail() throws MessagingException {
        // 复杂邮件
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        // 组装
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("求职者信息");
        helper.setText("<p style='color:red'>您好，这是我的简历</p>", true);
        // 附件
        helper.addAttachment("Java_姜磊_求职简历.pdf", new File("F:\\Java_姜磊_求职简历.pdf"));

        helper.setTo("1274684480@qq.com");
        helper.setFrom("1274684480@qq.com");
        javaMailSender.send(mimeMessage);

        return R.success();
    }

}
