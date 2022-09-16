package com.myservices.loggingservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.HttpServletResponse;

@SpringBootTest
@AutoConfigureMockMvc
class LoggingserviceApplicationTests {

    @Autowired
    MockMvc mockMvc;


    @Test
    void getLogs() throws Exception {

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/logging/getlogs")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        assert (mvcResult.getResponse().getStatus() == HttpServletResponse.SC_OK);
    }

    @Test
    void getlog() throws Exception {

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/logging/getlog/1")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        assert (mvcResult.getResponse().getStatus() == HttpServletResponse.SC_OK);
    }

    @Test
    void postLog() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/logging/postlog")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"id\":100,\"loginfo\":\"test information\",\"objectname\":\"obj5\"}]")
        ).andReturn();
        assert (mvcResult.getResponse().getStatus() == HttpServletResponse.SC_CREATED);
    }
}
