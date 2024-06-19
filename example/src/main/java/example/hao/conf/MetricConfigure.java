package example.hao.conf;

import io.micrometer.core.aop.TimedAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by hào on 2023/9/5
 */
@Configuration
public class MetricConfigure {

    @Bean
    public TimedAspect timedAspect() {
        return new TimedAspect();
    }
}
