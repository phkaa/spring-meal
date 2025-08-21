package com.project.domain.concurrency.service;

import com.project.domain.concurrency.entity.Stock;
import com.project.domain.concurrency.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;

    public void save(Stock stock) {
        this.stockRepository.save(stock);
    }

    public Integer getRemainStockCount(Long id) {
        return this.stockRepository.findById(id)
                .map(Stock::getStockCount)
                .orElse(0);
    }

    public Integer discountStockCount(Long id) {
        Stock stock = this.stockRepository.findById(id).get();
        stock.discountStockCount();

        return stock.getStockCount();
    }
}
