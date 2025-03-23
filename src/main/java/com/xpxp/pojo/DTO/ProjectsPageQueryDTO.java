package com.xpxp.pojo.DTO;

import lombok.Data;

@Data
public class ProjectsPageQueryDTO {
    private int id;
    private String title;
    private String content;
    private int page;
    private int pageSize;
}
