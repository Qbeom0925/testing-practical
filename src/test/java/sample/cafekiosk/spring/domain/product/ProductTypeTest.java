package sample.cafekiosk.spring.domain.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import sample.cafekiosk.spring.IntegrationTestSupport;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductTypeTest extends IntegrationTestSupport {

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

    @Disabled
    @CsvSource({
            "HANDMADE, false",
            "BOTTLE, true",
            "BAKERY, true"
    })
    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    void containsStockType3(ProductType productType, boolean expected) {
        boolean b = ProductType.containsStockType(productType);

        Assertions.assertThat(b).isEqualTo(expected);
    }



    private static Stream<Arguments> provideProductTypesForCheckingStockType() {
        return Stream.of(
                Arguments.of(ProductType.HANDMADE, false),
                Arguments.of(ProductType.BOTTLE, true),
                Arguments.of(ProductType.BAKERY, true)
        );
    }

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @MethodSource("provideProductTypesForCheckingStockType")
    @ParameterizedTest
    void containsStockType4(ProductType productType, boolean expected) {
        // when
        boolean result = ProductType.containsStockType(productType);

        // then
        assertThat(result).isEqualTo(expected);
    }



}