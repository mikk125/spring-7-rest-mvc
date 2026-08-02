package guru.springframework.spring7restmvc.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.HttpLogFormatter;
import org.zalando.logbook.Sink;
import org.zalando.logbook.json.JsonHttpLogFormatter;
import org.zalando.logbook.logstash.LogstashLogbackSink;

@Configuration
public class LogbookConfig {

    @Bean
    public Sink LogboookLogStach() {
        HttpLogFormatter formatter = new JsonHttpLogFormatter();

        return new LogstashLogbackSink(formatter);
    }
}
