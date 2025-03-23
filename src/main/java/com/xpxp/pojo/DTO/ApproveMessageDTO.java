package com.xpxp.pojo.DTO;

import lombok.Data;

@Data
public class ApproveMessageDTO {
    private Integer senderId;
    private Integer projectId;
    private String status;
    private String assign;
    private String title;
}
