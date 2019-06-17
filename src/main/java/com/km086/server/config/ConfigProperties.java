package com.km086.server.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app")
public class ConfigProperties {

    private int needPayProcessNum;

    private int noNeedPayProcessNum;

    private String imageDir;

    private String apkDir;

    private String superPassword;
}
