package com.xpxp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "team.jwt")
@Data
public class JwtProperties {
    //用户端jwt令牌
    private String userSecretKey;
    private long userTtl;
    private String userTokenName;
}
