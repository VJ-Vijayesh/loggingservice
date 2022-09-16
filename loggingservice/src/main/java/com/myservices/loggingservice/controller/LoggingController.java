package com.myservices.loggingservice.controller;


import com.myservices.loggingservice.exceptions.LoggingClientException;
import com.myservices.loggingservice.model.Logs;
import org.apache.commons.codec.binary.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController()
@RequestMapping("/logging")
public class LoggingController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private Environment environment;


    @RequestMapping("/getlogs")
    @ResponseStatus(HttpStatus.OK)
    public List<Logs> getLoggedData() {
        ResponseEntity<List<Logs>> responseEntity = restTemplate.exchange("http://cassaloggingservice/nosqllogging/getlogs",
                HttpMethod.GET,
                new HttpEntity<>(
                        new HttpHeaders() {{
                            String auth = "Basic " + Base64.encodeBase64String((environment.getProperty("cassauser") + ":" + environment.getProperty("cassapassword")).getBytes()).toString();
                            set("Authorization", auth);
                        }}
                ),
                new ParameterizedTypeReference<List<Logs>>() {
                });
        if (responseEntity.getStatusCodeValue() > 299) {
            throw new LoggingClientException("Client Exception");
        }
        return responseEntity.getBody();
    }

    @RequestMapping("/getlog/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Logs getLog(@PathVariable Integer id) {

        try {

            Logs log = restTemplate.exchange(
                    "http://cassaloggingservice/nosqllogging/getlog/" + id.toString(),
                    HttpMethod.GET,
                    new HttpEntity<>(
                            new HttpHeaders() {{
                                String auth = "Basic " + Base64.encodeBase64String((environment.getProperty("cassauser") + ":" + environment.getProperty("cassapassword")).getBytes()).toString();
                                set("Authorization", auth);
                            }}
                    )
                    , Logs.class).getBody();
            return log;
        } catch (Exception ex) {
            throw new LoggingClientException("Client Exception " + ex.getLocalizedMessage());
        }
    }

    @PostMapping("/postlog")
    @ResponseStatus(HttpStatus.CREATED)
    public String postLog(@RequestBody List<Logs> logs) {

        try {
            return restTemplate.postForObject("http://cassaloggingservice/nosqllogging/writelog",
                    new HttpEntity<>(
                            logs,
                            new HttpHeaders() {{
                                String auth = "Basic " + Base64.encodeBase64String((environment.getProperty("cassauser") + ":" + environment.getProperty("cassapassword")).getBytes()).toString();
                                set("Authorization", auth);
                            }}
                    ),
                    String.class);
        } catch (Exception ex) {
            throw new LoggingClientException("Client Exception " + ex.getLocalizedMessage());
        }

    }



}
