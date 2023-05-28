package sample.cafekiosk.spring.api.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.spring.BaseEntity;
import sample.cafekiosk.spring.IntegrationTestSupport;
import sample.cafekiosk.spring.api.controller.product.dto.response.ProductResponse;
import sample.cafekiosk.spring.api.service.client.mail.MailSendClient;
import sample.cafekiosk.spring.api.service.order.OrderStaticsService;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistory;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.order.OrderRepository;
import sample.cafekiosk.spring.domain.order.OrderStatus;
import sample.cafekiosk.spring.domain.orderProduct.OrderProductRepository;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

class OrderStaticsServiceTest extends IntegrationTestSupport {

    @Autowired
    OrderProductRepository orderProductRepository;

    @Autowired
    private OrderStaticsService orderStaticsService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MailSendHistoryRepository mailSendHistoryRepository;

    @MockBean
    private MailSendClient mailSendClient;

    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAll();
        orderRepository.deleteAll();
        productRepository.deleteAll();
        mailSendHistoryRepository.deleteAll();
    }

    @DisplayName("결제완료 주문들을 조회하여 매출 통계 메일을 전송한다.")
    @Test
    public void sendOrderStatisticsMail() throws Exception{
        //given
        LocalDateTime now = LocalDateTime.of(2023, 3, 5, 10, 0);

        Product product1 = createProduct("001", HANDMADE, 4000);
        Product product2 = createProduct("002", HANDMADE, 4000);
        Product product3 = createProduct("003", HANDMADE, 4000);
        List<Product> product = List.of(product1, product2, product3);
        productRepository.saveAll(product);

        Order order1 = createPaymentCompletedOrder(now, product);
        Order order2 = createPaymentCompletedOrder(now, product);
        Order order3 = createPaymentCompletedOrder(LocalDateTime.of(2023,3,5,23,59), product);
        Order order4 = createPaymentCompletedOrder(LocalDateTime.of(2023,3,6,0,0), product);

        Mockito.when(mailSendClient.sendMail(any(String.class),any(String.class),any(String.class),any(String.class)))
                .thenReturn(true);

        //when
        boolean result = orderStaticsService.sendOrderStatisticsMail(LocalDate.of(2023, 3, 5), "test@test.com");

        //then
        assertThat(result).isTrue();

        List<MailSendHistory> histories = mailSendHistoryRepository.findAll();
        assertThat(histories).hasSize(1)
                .extracting("content")
                .contains(String.format("총 매출 합계는 %s원입니다.", 36000));
    }

    private Order createPaymentCompletedOrder(LocalDateTime now, List<Product> product) {
        Order order = Order.builder()
                .products(product)
                .orderstatus(OrderStatus.PAYMENT_COMPLETED)
                .registeredDateTime(now)
                .build();
        return orderRepository.save(order);
    }

    private Product createProduct(String productNumber, ProductType productType, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .productType(productType)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("메뉴 이름")
                .price(price)
                .build();
    }

}