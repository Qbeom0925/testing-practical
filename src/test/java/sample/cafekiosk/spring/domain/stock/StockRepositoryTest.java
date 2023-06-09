package sample.cafekiosk.spring.domain.stock;

import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import sample.cafekiosk.spring.IntegrationTestSupport;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StockRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private StockRepository stockRepository;

    @DisplayName("상품번호 리스트로 재고를 조회한다.")
    @Test
    void findAllByProductNumberIn() throws Exception{
        //given
        Stock stock1 = Stock.create("001", 1);
        Stock stock2 = Stock.create("002", 1);
        Stock stock3 = Stock.create("003", 1);
        stockRepository.saveAll(List.of(stock1,stock2,stock3));

        //when
        stockRepository.findAllByProductNumberIn(List.of("001","002"));

        //then
        Assertions.assertThat(stockRepository.findAllByProductNumberIn(List.of("001","002")))
                .hasSize(2)
                .extracting("productNumber","quantity")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("001",1),
                        Tuple.tuple("002",1)
                );
    }

}