package com.project.api.json;

import com.project.api.BaseIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Json 컨트롤러 테스트
 */
public class JsonControllerTest extends BaseIntegrationTest {
    @Test
    @DisplayName("Json Locale 한국어 테스트")
    public void testGetJsonLocalByKo() throws Exception {
        mockMvc.perform(get("/jsons/locale")
                        .header("Accept-Language", "ko")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").value("콘텐츠 입니다."))
                .andExpect(jsonPath("$.data.title").value("한글 타이틀"));
    }

    @Test
    @DisplayName("Json Locale 영문 테스트")
    public void testGetJsonLocalByEn() throws Exception {
        mockMvc.perform(get("/jsons/locale")
                        .header("Accept-Language", "en")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").value("콘텐츠 입니다."))
                .andExpect(jsonPath("$.data.title").value("영문 타이틀"));
    }

}
