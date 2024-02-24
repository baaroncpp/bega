package com.bwongo.base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 2/24/24
 **/
@ConfigurationProperties
@Service
public class StorageConfig {

    /**
     * Folder location for storing files
     */
    @Value("${app.file-storage}")
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
