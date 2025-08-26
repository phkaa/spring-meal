CREATE TABLE stock (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '고유 식별자',
    stock_count INT NOT NULL COMMENT '재고 수량'
) COMMENT='재고 정보를 저장하는 테이블';