package com.example.groovy_app

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.RequestMapping

@FeignClient("nextservice")
interface NextServiceClient {
    @RequestMapping("/")
    getRoot()
}
