package com.xpxp.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BlogVO {
    private String title;
    private String content;
    private String authorName;
    private String createdAt;
    private String tags;
}
