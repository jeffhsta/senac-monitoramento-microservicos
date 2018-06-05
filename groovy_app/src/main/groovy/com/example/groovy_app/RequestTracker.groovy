package com.example.groovy_app

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import static java.time.LocalDateTime.now
import static java.time.temporal.ChronoUnit.MILLIS
import static java.util.UUID.randomUUID

@Component
class RequestTracker extends OncePerRequestFilter {

    @Autowired
    LogWrapper logWrapper
    @Autowired
    HttpServletRequest httpServletRequest

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        def start = now()
        def reqId = request.getHeader('X-Request-Id')
        if (!reqId) {
            reqId = randomUUID().toString();
        }
        httpServletRequest.setAttribute('X-Request-Id', reqId)
        response.setHeader('X-Request-Id',reqId)
        doFilter(request, response, filterChain);
        def deltaTime = start.until(now(), MILLIS)
        logWrapper.debug(
            [time_in_ms: deltaTime.toString(), status_code: response.status],
            [type: "http_server"]
        )
    }
}
