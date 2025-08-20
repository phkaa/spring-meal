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
     * @return 응답
     */
    @PostMapping("/v1")
    public CommonResponse<Integer> saveStockCount(
            @RequestParam(defaultValue = "100") int stockCount
    ) {
        concurrencyApplication.saveStockCount(stockCount);

        return CommonResponse.success(stockCount);
    }

    /**
     * 남은 재고수 가져오기
     *
     * @return 남은 재고수
     */
    @GetMapping("/v1")
    public CommonResponse<Integer> getRemainStockCount() {
        return CommonResponse.success(concurrencyApplication.getRemainStockCount());
    }

    /**
     * 동시성 처리 없이 남은 재고수 가져오기
     * 
     * @return 남은 재고수
     */
    @GetMapping("/no/v1")
    public CommonResponse<Integer> getRemainStockCountByNoConcurrency() {
        return CommonResponse.success(concurrencyApplication.getRemainStockCountByNoConcurrency());
    }
}
