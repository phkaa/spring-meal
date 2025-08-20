package com.project.domain.applications;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConcurrencyApplication {
    private static int stockCount = 0;

    /**
     * 동시성 없이 재고수 감소
     * 
     * @return 남은 재고수
     */
    public int getRemainStockCountByNoConcurrency() {
        stockCount -= 1;

        log.info("남은 재고수: " + stockCount);

        return stockCount;
    }

    public void saveStockCount(int paramStockCount) {
        stockCount = paramStockCount;
    }

    public Integer getRemainStockCount() {
        return stockCount;
    }
}
