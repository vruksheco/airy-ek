package co.airy.spring.web.config;

import com.segment.analytics.Analytics;
import com.segment.analytics.messages.IdentifyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(SegmentProperties.class)
@ConditionalOnProperty("segment.analytics.enabled")
public class SegmentAnalyticsAutoConfiguration {

    @Autowired
    private SegmentProperties properties;

    @Bean
    public Analytics segmentAnalytics(@Value("${core.id}") final String coreId) {
        if (properties.getEnabled().equals("true")) {
            Analytics analytics = Analytics.builder(properties.getWriteKey()).build();
            analytics.enqueue(IdentifyMessage.builder()
                    .userId(coreId)
            );
            return analytics;
        }
        return null;
    }
}
