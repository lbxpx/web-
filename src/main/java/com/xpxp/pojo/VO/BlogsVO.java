package com.xpxp.pojo.VO;

import com.xpxp.pojo.entity.Category;
import lombok.Data;

import java.util.List;

@Data
public class BlogsVO {
    private int id;
    private String title;
    private String tags;
    private String author;
    private String image;
    private String date;
}
