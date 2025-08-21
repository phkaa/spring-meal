package com.project.api.concurrency;

import com.project.api.BaseIntegrationTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 동시성 컨트롤러 테스트
 */
public class ConcurrencyControllerTest extends BaseIntegrationTest {
    private final static int SETTING_STOCK_COUNT = 10;
    private final static int THREAD_COUNT = 10;
    private static int stockId = 0;

    @BeforeAll
    @DisplayName("재고수 설정")
    public void beforeAll() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/concurrencies/v1")
                        .param("stockCount", String.valueOf(SETTING_STOCK_COUNT)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        stockId = getData(mvcResult).asInt();
    }

    @AfterAll
    @DisplayName("남은 재고수 가져오기")
    public void afterAll() throws Exception {
        mockMvc.perform(get("/concurrencies/{0}/v1", stockId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(SETTING_STOCK_COUNT - THREAD_COUNT));
    }


    @Test
    @DisplayName("동시성 처리 없이 남은 재고수 가져오기 테스트")
    public void testGetRemainStockCountByNoConcurrency() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        for (int index = 0; index < THREAD_COUNT; index++) {
            executorService.submit(() -> {
                try {
                    mockMvc.perform(get("/concurrencies/{0}/no/v1", stockId))
                            .andExpect(status().isOk());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
    }
}
