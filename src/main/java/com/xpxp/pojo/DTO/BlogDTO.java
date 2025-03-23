package com.xpxp.pojo.DTO;

import lombok.Data;

@Data
public class BlogDTO {
    private Integer authorId;
    private String title;
    private String content;
    private String authorName;
    private String image;
    private String tags;
}
