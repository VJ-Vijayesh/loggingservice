package com.myservices.loggingservice;

import com.myservices.loggingservice.controller.LoggingController;
import com.myservices.loggingservice.model.Logs;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class LoggingserviceApplicationUnitTests {

    @InjectMocks
    LoggingController loggingController;

    @Mock
    RestTemplate restTemplate;

    @Mock
    Environment environment;

    @Test
    void ut_getLogs() throws Exception {

        Logs logs = new Logs(100, "test info log", "test object");
        List<Logs> logsList = new ArrayList<Logs>();


        Mockito.when(restTemplate.exchange("http://cassaloggingservice/nosqllogging/getlogs",
                        HttpMethod.GET,
                        new HttpEntity<>(
                                new HttpHeaders() {{
                                    String auth = "Basic " + Base64.encodeBase64String((environment.getProperty("cassauser") + ":" + environment.getProperty("cassapassword")).getBytes()).toString();
                                    set("Authorization", auth);
                                }}
                        ),
                        new ParameterizedTypeReference<List<Logs>>() {
                        }))
                .thenReturn(new ResponseEntity(logsList, HttpStatus.OK));

        List<Logs> result = loggingController.getLoggedData();
        Assertions.assertEquals(result, logsList);
    }

    @Test
    void ut_getlogforid() throws Exception {
        Logs logs = new Logs(100, "test info", "test object");
        Mockito.when(
//                        restTemplate.getForObject("http://cassaloggingservice/nosqllogging/getlog/100", Logs.class)
                        restTemplate.exchange(
                                "http://cassaloggingservice/nosqllogging/getlog/100",
                                HttpMethod.GET,
                                new HttpEntity<>(
                                        new HttpHeaders() {{
                                            String auth = "Basic " + Base64.encodeBase64String((environment.getProperty("cassauser") + ":" + environment.getProperty("cassapassword")).getBytes()).toString();
                                            set("Authorization", auth);
                                        }}
                                )
                                , Logs.class)
                )
                .thenReturn(new ResponseEntity<>(logs,HttpStatus.OK));
        Logs result = loggingController.getLog(100);
        Assertions.assertEquals(logs, result);
    }

    @Test
    void ut_postLog() throws Exception {
        List<Logs> logsList = new ArrayList<>();
        logsList.add(new Logs(100, "test info log", "test obj"));
        logsList.add(new Logs(101, "test info log", "test obj"));
        Mockito.when(
//                        restTemplate.postForObject("http://cassaloggingservice/nosqllogging/writelog", logsList, String.class)
                        restTemplate.postForObject("http://cassaloggingservice/nosqllogging/writelog",
                                new HttpEntity<>(
                                        logsList,
                                        new HttpHeaders() {{
                                            String auth = "Basic " + Base64.encodeBase64String((environment.getProperty("cassauser") + ":" + environment.getProperty("cassapassword")).getBytes()).toString();
                                            set("Authorization", auth);
                                        }}
                                ),
                                String.class)
                )
                .thenReturn("Created");
        String result = loggingController.postLog(logsList);
        Assertions.assertEquals("Created", result);
    }
}
