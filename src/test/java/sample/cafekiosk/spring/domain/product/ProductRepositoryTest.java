package sample.cafekiosk.spring.domain.product;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("원하는 판매상태를 가진 상품들을 조회한다.")
    @Test
    public void findAllBuSellingStatusIn() throws Exception{
        //given
        Product product1 = createProduct("001", ProductSellingStatus.SELLING, "아메리카노", 4000);
        Product product2 = createProduct("002", ProductSellingStatus.HOLD, "카페라떼", 4500);
        Product product3 = createProduct("003", ProductSellingStatus.STOP_SELLING, "팥빙수", 7000);
        productRepository.saveAll(List.of(product1,product2,product3));

        //when
        List<Product> products = productRepository.findAllBySellingStatusIn(List.of(ProductSellingStatus.SELLING, ProductSellingStatus.HOLD));

        //then
        assertThat(products)
                .hasSize(2)
                .extracting("productNumber","name","sellingStatus")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("001","아메리카노",ProductSellingStatus.SELLING),
                        Tuple.tuple("002","카페라떼",ProductSellingStatus.HOLD)
                );
    }

    @DisplayName("상품번호 리스트로 상품들을 조회한다.")
    @Test
    void findAllByProductNumberIn() throws Exception{
        //given
        Product product1 = createProduct("001", ProductSellingStatus.SELLING, "아메리카노", 4000);
        Product product2 = createProduct("002", ProductSellingStatus.HOLD, "카페라떼", 4500);
        Product product3 = createProduct("003", ProductSellingStatus.STOP_SELLING, "팥빙수", 7000);
        productRepository.saveAll(List.of(product1,product2,product3));

        //when
        List<Product> products = productRepository.findAllByProductNumberIn(List.of("001","002"));

        //then
        assertThat(products)
                .hasSize(2)
                .extracting("productNumber","name","sellingStatus")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("001","아메리카노",ProductSellingStatus.SELLING),
                        Tuple.tuple("002","카페라떼",ProductSellingStatus.HOLD)
                );
    }

    @DisplayName("가장 마지막으로 저장한 상품의 상품번호를 읽어온다.")
    @Test
    void findAllByProductLastestProduct(){
        //given
        Product product1 = createProduct("001", ProductSellingStatus.SELLING, "아메리카노", 4000);
        Product product2 = createProduct("002", ProductSellingStatus.HOLD, "카페라떼", 4500);
        Product product3 = createProduct("003", ProductSellingStatus.STOP_SELLING, "팥빙수", 7000);
        productRepository.saveAll(List.of(product1,product2,product3));

        //when
        String latestProduct = productRepository.findLatestProduct();

        //then
        assertThat(latestProduct).isEqualTo("003");
    }


    @DisplayName("가장 마지막으로 저장한 상품의 상품번호를 읽어올 때, 상품이 하나도 없는 겨웅에는 Null을 반환한다.")
    @Test
    void findAllByProductLastestProductWhenProductIsEmpty(){
        //given
        String latestProduct = productRepository.findLatestProduct();

        //then
        assertThat(latestProduct).isNull();
    }

    private Product createProduct(String productNumber, ProductSellingStatus sellingStatus, String name, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .productType(ProductType.HANDMADE)
                .sellingStatus(sellingStatus)
                .name(name)
                .price(price)
                .build();
    }


}
