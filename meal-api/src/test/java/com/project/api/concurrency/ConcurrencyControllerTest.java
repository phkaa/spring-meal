package com.project.api.concurrency;

import com.project.api.BaseIntegrationTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
    private final static int STOCK_COUNT = 100;
    private final static int THREAD_COUNT = 100;

    @BeforeAll
    @DisplayName("재고수 설정")
    public void beforeAll() throws Exception {
        mockMvc.perform(post("/concurrencies/v1")
                        .param("stockCount", String.valueOf(STOCK_COUNT)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @AfterAll
    @DisplayName("남은 재고수 가져오기")
    public void afterAll() throws Exception {
        mockMvc.perform(get("/concurrencies/v1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(STOCK_COUNT - THREAD_COUNT));
    }


    @Test
    @DisplayName("동시성 처리 없이 남은 재고수 가져오기 테스트")
    public void testGetRemainStockCountByNoConcurrency() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        for (int index = 0; index < THREAD_COUNT; index++) {
            executorService.submit(() -> {
                try {
                    mockMvc.perform(get("/concurrencies/no/v1"))
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
