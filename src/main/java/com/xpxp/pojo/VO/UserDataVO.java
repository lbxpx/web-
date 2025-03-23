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
public class UserDataVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private String image;
    private String email;
    private String sex;
    private String description;
    private String school;
}
