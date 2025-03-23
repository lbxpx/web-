package com.xpxp.pojo.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NewProjectDTO {
    private Integer userId;
    private String title;
    private String description;
    private String publisherName;
    private String image;
    private List<Integer> categoryIds = new ArrayList<>();
}
