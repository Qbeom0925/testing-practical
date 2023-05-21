package sample.cafekiosk.spring.domain.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTypeTest {

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    void containsStockType() {
        ProductType givenType = ProductType.HANDMADE;

        boolean b = ProductType.containsStockType(givenType);

        Assertions.assertThat(b).isFalse();
    }

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    void containsStockType2() {
        ProductType givenType = ProductType.BAKERY;

        boolean b = ProductType.containsStockType(givenType);

        Assertions.assertThat(b).isTrue();
    }

}