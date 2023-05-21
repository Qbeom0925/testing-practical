package sample.cafekiosk.spring.domain.stock;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class StockTest {

    @DisplayName("재고의 수량이 제공된 수량보다 적은지 확인한다.")
    @Test
    public void isQuantityLessThen() throws Exception{
        //given
        Stock stock = Stock.create("001", 1);
        int quantity = 2;

        //when
        boolean quantityLessThan = stock.isQuantityLessThan(quantity);

        //then
        assertThat(quantityLessThan).isTrue();
    }


    @DisplayName("재고를 주어진 개수만큼 차감할 수 있다.")
    @Test
    public void deductQuantity(){
        //given
        Stock stock = Stock.create("001", 1);
        int quantity = 1;

        //when
        stock.deductQuantity(quantity);

        //then
        assertThat(stock.getQuantity()).isZero();
    }

    @DisplayName("재고보다 많은 수의 수량으로 차감 시도하는 경우 예외가 발생한다.")
    @Test
    public void deductQuantity2(){
        //given
        Stock stock = Stock.create("001", 1);
        int quantity = 2;

        //when then
        assertThatThrownBy(() -> stock.deductQuantity(quantity))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("차감할 재고 수량이 부족합니다.");
    }

}
