package sample.cafekiosk.spring.api.service.product;

import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.spring.IntegrationTestSupport;
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.controller.product.dto.response.ProductResponse;
import sample.cafekiosk.spring.api.service.product.request.ProductCreateServiceRequest;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.*;
import static sample.cafekiosk.spring.domain.product.ProductType.*;

class ProductServiceTest extends IntegrationTestSupport {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAllInBatch();
    }

    @DisplayName("신규 상품을 등록한다. 상품번호는 가장 최근 상품의 상품번호에서 1 증가한 값이다.")
    @Test
    public void test(){
        //given
        Product product1 = createProduct("001", SELLING, HANDMADE, "아메리카노", 4000);
        productRepository.save(product1);
        ProductCreateServiceRequest request = ProductCreateServiceRequest.builder()
                .sellingStatus(SELLING)
                .productType(HANDMADE)
                .name("카푸치노")
                .price(5000)
                .build();

        //when
        ProductResponse response = productService.createProduct(request);

        //then
        assertThat(response)
                .extracting("productNumber","productType","sellingStatus","name","price")
                .contains("002", HANDMADE, SELLING,"카푸치노",5000);
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(2)
                .extracting("productNumber","productType","sellingStatus","name","price")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("001", HANDMADE, SELLING,"아메리카노",4000),
                        Tuple.tuple("002", HANDMADE, SELLING,"카푸치노",5000)
                );
     }

     @DisplayName("상품이 하나도 없는 경우 신규 상품을 등록하면 상품번호는 001이다.")
     @Test
     public void createProduct(){
         //given
         ProductCreateServiceRequest request = ProductCreateServiceRequest.builder()
                 .productType(HANDMADE)
                 .sellingStatus(SELLING)
                 .name("카푸치노")
                 .price(5000)
                 .build();

         //when
         ProductResponse response = productService.createProduct(request);

         //then
         assertThat(response)
                 .extracting("productNumber","productType","sellingStatus","name","price")
                 .contains("001", HANDMADE, SELLING,"카푸치노",5000);

         List<Product> products = productRepository.findAll();
         assertThat(products).hasSize(1)
                 .extracting("productNumber","productType","sellingStatus","name","price")
                 .containsExactlyInAnyOrder(
                         Tuple.tuple("001", HANDMADE, SELLING,"카푸치노",5000)
                 );

     }

    private Product createProduct(String productNumber, ProductSellingStatus sellingStatus, ProductType productType, String name, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .productType(productType)
                .sellingStatus(sellingStatus)
                .name(name)
                .price(price)
                .build();
    }

}