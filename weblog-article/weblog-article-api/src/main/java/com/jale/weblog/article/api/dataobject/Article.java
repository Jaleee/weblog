package com.jale.weblog.article.api.dataobject;

import lombok.Data;

import java.io.Serializable;

@Data
public class Article implements Serializable {

    private Long id;

    private Long userId;

    private String title;

}
