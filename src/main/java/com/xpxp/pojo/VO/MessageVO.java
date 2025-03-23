package com.xpxp.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MessageVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String projectId;
    private String senderId;
    private String receiverId;
    private String projectName;
    private String senderName;
    private String receiverName;
    private String reason;
    private String strengths;
    private String status;
}
