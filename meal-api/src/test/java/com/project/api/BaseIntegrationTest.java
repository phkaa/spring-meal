package com.project.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@IntegrationTest
public abstract class BaseIntegrationTest {
    @Autowired
    protected MockMvc mockMvc;

    /**
     * MvcResult에서 JSON 응답의 data 필드만 추출
     *
     * @param mvcResult MockMvc 수행 결과
     * @return data 필드(JsonNode)
     */
    public static JsonNode getData(MvcResult mvcResult) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            String responseBody = mvcResult.getResponse().getContentAsString();

            JsonNode jsonNode = objectMapper.readTree(responseBody);

            return jsonNode.get("data");
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse MvcResult JSON", e);
        }
    }
}
