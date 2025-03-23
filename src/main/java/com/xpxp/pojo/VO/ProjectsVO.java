package com.xpxp.pojo.VO;

import com.xpxp.pojo.entity.Category;
import lombok.Data;

import java.util.List;

@Data
public class ProjectsVO {
    private int id;
    private String title;
    private List<Category> categories;
    private String publisherName;
    private String image;
}
