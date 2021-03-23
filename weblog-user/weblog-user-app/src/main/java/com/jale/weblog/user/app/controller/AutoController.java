package com.jale.weblog.user.app.controller;

import com.jale.AutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auto")
public class AutoController {

    @Autowired
    private AutoService autoService;

    @RequestMapping("/hello")
    private void hello() {
        autoService.hello();
    }

}
