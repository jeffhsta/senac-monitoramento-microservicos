package com.example.groovy_app

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import static org.springframework.http.ResponseEntity.status

@RestController
class RootController implements ErrorController {
    @Value('${service_name}')
    String servicename

    @Value('${nextservice.ribbon.listOfServers}')
    List<String> nextServices;

    @Autowired
    LogWrapper logWrapper

    @Autowired
    NextServiceClient nextServiceClient


    @RequestMapping()
    def getRoot() {
        logWrapper.debug([message: 'Got it!'])
        def response = [:]
        if (nextServices.size() > 0) {
            try {
                response = nextServiceClient.getRoot()
            } catch (Exception ignored) {
                //log error...
            }
        }
        response[servicename] = "OK"
        response
    }

    @RequestMapping("/error")
    ResponseEntity getError() {
        status(INTERNAL_SERVER_ERROR)
            .body([message: "Something went wrong"])
    }

    @Override
    String getErrorPath() {
        return "/error"
    }
}
