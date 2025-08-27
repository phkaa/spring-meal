package com.project.domain.applications;

import com.project.domain.concurrency.entity.Stock;
import com.project.domain.concurrency.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConcurrencyApplication {
    private final StockService stockService;
    private final RedissonClient redissonClient;

    private final static String LOCK_NAME_PREFIX = "stock_count:";

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

    /**
     * 동시성 없이 재고 감소
     *
     * @param id 아이디
     */
    public void discountByNoConcurrency(Long id) {
        int remainStockCount = this.stockService.discountStockCount(id);

        log.info("남은 재고수: {}", remainStockCount);
    }

    /**
     * redisson 으로 동시성 처리 후 재고 감소
     *
     * @param id 아이디
     */
    public void discountByRedissonLock(Long id) {
        RLock lock = null;
        boolean locked = false;

        try {
            lock = redissonClient.getLock(String.format("%s%d", LOCK_NAME_PREFIX, id));
            locked = lock.tryLock(5, 10, TimeUnit.SECONDS); // 5초 대기, 10초 점유

            if (!locked) {
                throw new Exception("락 획득 실패");
            }

            int remainStockCount = this.stockService.discountStockCount(id);

            log.info("남은 재고수: {}", remainStockCount);
        } catch (Exception e) {
            throw new RuntimeException(String.format("%s[%s]", "재고 감소 실패", e.getMessage()));
        } finally {
            if (lock != null && locked && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
