package co.airy.spring.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("segment.analytics")
public class SegmentProperties {

    private String enabled;
    private String writeKey = "xCK4HrNJxaP5pXClLxgShD4FkAp5PG53";

    public String getEnabled() { return enabled; }
    public String getWriteKey() { return writeKey; }

    public void setEnabled(String enabled) { this.enabled = enabled; }
    public void setWriteKey(String writeKey) { this.writeKey = writeKey; }
}
