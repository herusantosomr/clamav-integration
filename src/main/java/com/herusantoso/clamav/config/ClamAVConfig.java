package com.herusantoso.clamav.config;

import com.herusantoso.clamav.properties.ClamAVProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@Configuration
public class ClamAVConfig {

    private static Logger LOGGER = LoggerFactory.getLogger(ClamAVConfig.class);

    @Autowired
    private ClamAVProperties clamAVProperties;

    @Bean
    public ClamAVClient clamAVClient() {
        return new ClamAVClient(clamAVProperties.getClamAvHost(), clamAVProperties.getClamAvPort());
    }

    @PostConstruct
    public void init() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", "clamd --version");
        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                LOGGER.info("Clamd version information = {} ", line);
            }
            int exitCode = process.waitFor();
            LOGGER.info("\nExited with error code = {}  ", exitCode);
        } catch (Exception e) {
            LOGGER.error("Exception occurred while getting clamd version = {} ", e.getMessage());
        }
    }

}
