package com.xpxp.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("pro_categories")
public class ProjectCategories {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer projectId;
    private Integer categoryId;

}
