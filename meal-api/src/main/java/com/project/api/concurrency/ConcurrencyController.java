package com.project.api.concurrency;

import com.project.common.response.CommonResponse;
import com.project.domain.applications.ConcurrencyApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 동시성 테스트
 */
@RestController
@RequestMapping("/concurrencies")
@RequiredArgsConstructor
public class ConcurrencyController {
    private final ConcurrencyApplication concurrencyApplication;

    /**
     * 재고수 설정
     *
     * @param stockCount 재고수
     * @return 재고 아이디
     */
    @PostMapping("/v1")
    public CommonResponse<Long> saveStockCount(
            @RequestParam(defaultValue = "100") int stockCount
    ) {
        return CommonResponse.success(concurrencyApplication.saveStockCount(stockCount));
    }

    /**
     * 남은 재고수 가져오기
     *
     * @return 남은 재고수
     */
    @GetMapping("/{id:\\d+}/v1")
    public CommonResponse<Integer> getRemainStockCount(
            @PathVariable Long id
    ) {
        return CommonResponse.success(concurrencyApplication.getRemainStockCount(id));
    }

    /**
     * 동시성 처리 없이 남은 재고 감소
     */
    @GetMapping("/{id:\\d+}/no/v1")
    public CommonResponse<Void> discountByNoConcurrency(
            @PathVariable Long id
    ) {
        concurrencyApplication.discountByNoConcurrency(id);

        return CommonResponse.success();
    }

    /**
     * redisson 으로 동시성 처리 후 재고 감소
     */
    @GetMapping("/{id:\\d+}/redisson/v1")
    public CommonResponse<Void> discountByRedissonLock(
            @PathVariable Long id
    ) {
        concurrencyApplication.discountByRedissonLock(id);

        return CommonResponse.success();
    }
}
