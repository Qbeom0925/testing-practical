package sample.cafekiosk.spring.domain.order;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.BaseEntity;
import sample.cafekiosk.spring.domain.orderProduct.OrderProduct;
import sample.cafekiosk.spring.domain.product.Product;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Order extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderstatus;

    private int totalPrice;
    private LocalDateTime registeredDateTime;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts;

    @Builder
    public Order(List<Product> products, OrderStatus orderstatus, LocalDateTime registeredDateTime, List<OrderProduct> orderProducts) {
        this.orderstatus = orderstatus;
        this.totalPrice = calculateTotalPrice(products);
        this.registeredDateTime = registeredDateTime;
        this.orderProducts = products.stream()
                .map(product -> new OrderProduct(this, product))
                .collect(Collectors.toList());
    }

    public static Order create(List<Product> products, LocalDateTime registeredDateTime) {
        return Order.builder()
                .products(products)
                .orderstatus(OrderStatus.INIT)
                .registeredDateTime(registeredDateTime)
                .build();
    }

    private int calculateTotalPrice(List<Product> products) {
        return products.stream()
                .mapToInt(Product::getPrice)
                .sum();
    }
}
