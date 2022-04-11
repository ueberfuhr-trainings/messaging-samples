package de.sample.messaging.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JMSConfig {

    @Bean
    MessageConverter defaultMessageConverter(ObjectMapper objectMapper) {
        var result = new MappingJackson2MessageConverter();
        result.setTargetType(MessageType.TEXT);
        result.setObjectMapper(objectMapper);
        return result;
    }

}
