package com.xpxp.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Joinapplications implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer projectId;
    private Integer senderId;
    private Integer receiverId;
    private String reason;
    private String strengths;
    private String status;
    private Date created_at;
    private Date updated_at;
    private Integer expired;
}
