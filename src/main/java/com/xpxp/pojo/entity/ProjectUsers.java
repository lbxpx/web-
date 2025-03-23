package com.xpxp.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("project_users")
public class ProjectUsers {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer projectId;
    private Integer userId;
    private String title;
    private String assign;
    private String role;
}
