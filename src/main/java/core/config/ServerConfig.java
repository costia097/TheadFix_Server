package core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"core.service", "core.processor"} )
public class ServerConfig {
    @Bean
    public ObjectMapper createObjectMapper() {
        return new ObjectMapper();
    }
}
