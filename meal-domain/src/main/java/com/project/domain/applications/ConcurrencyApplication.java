package com.project.domain.applications;

import com.project.domain.concurrency.entity.Stock;
import com.project.domain.concurrency.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConcurrencyApplication {
    private final StockService stockService;

    /**
     * 동시성 없이 재고수 감소
     */
    @Transactional
    public void getRemainStockCountByNoConcurrency(Long id) {
        int remainStockCount = this.stockService.discountStockCount(id);

        log.info("남은 재고수: {}", remainStockCount);
    }

    /**
     * 신규 재고수 저장
     * 
     * @param stockCount 재고수
     * @return 아이디
     */
    @Transactional
    public Long saveStockCount(int stockCount) {
        Stock stock = Stock.builder().stockCount(stockCount).build();

        this.stockService.save(stock);

        return stock.getId();
    }

    /**
     * 남은 재고수 가져오기
     * 
     * @param id 아이디
     * @return 재고수
     */
    public Integer getRemainStockCount(Long id) {
        return this.stockService.getRemainStockCount(id);
    }
}
