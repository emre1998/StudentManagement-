package com.App.StudentManagement.Security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

        private String secret;
        private long expirationTime;
        private String issuer;
}

