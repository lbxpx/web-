package com.xpxp.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comments {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer blogId;
    private String author;
    private String content;
    private String date;
}
