package com.example.groovy_app

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Slf4j
import org.springframework.amqp.AmqpException
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

import javax.servlet.http.HttpServletRequest

import static java.time.LocalDateTime.now

@Component
@Slf4j
class LogWrapper {
    @Autowired
    RabbitTemplate rabbitTemplate

    @Value('${service_name}')
    String servicename

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    ObjectMapper objectMapper


    def debug(content, metadata = [:]) {
        metadata['timestamp'] = now().toString()
        metadata['source'] = servicename
        metadata['requestId'] = httpServletRequest.getAttribute('X-Request-Id') as String
        def logData = [
            metadata: metadata,
            content : content]
        def logJson = objectMapper.writeValueAsString(logData)
        try {
            rabbitTemplate.convertAndSend("logs", logJson)
        } catch (AmqpException ignored) {
        }
        log.info(logJson)
    }
}
