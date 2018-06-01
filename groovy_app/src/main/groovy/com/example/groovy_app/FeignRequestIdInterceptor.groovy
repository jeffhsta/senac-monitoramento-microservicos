package com.example.groovy_app

import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.servlet.http.HttpServletRequest

@Component
class FeignRequestIdInterceptor implements RequestInterceptor {


    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    void apply(RequestTemplate template) {
        template.header('X-Request-Id', httpServletRequest.getAttribute('X-Request-Id') as String)
    }
}
