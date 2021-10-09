package com.herusantoso.clamav.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "clamav")
@Configuration
public class ClamAVProperties {

    private String  clamAvHost;
    private Integer clamAvPort;

}