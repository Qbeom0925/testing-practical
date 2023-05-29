package sample.cafekiosk.spring.api.controller.product.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.api.service.product.request.ProductCreateServiceRequest;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor
public class ProductCreateRequest {


    @NotNull(message = "상품타입은 필수입니다.")
    private ProductType type;
    @NotNull(message = "판매상태는 필수입니다.")
    private ProductSellingStatus sellingStatus;
    @NotNull(message = "상품명은 필수입니다.")
    private String name;
    @Positive(message = "상품가격은 0보다 커야합니다.")
    private int price;

    @Builder
    public ProductCreateRequest(ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    public ProductCreateServiceRequest toServiceRequest() {
        return ProductCreateServiceRequest.builder()
                .type(type)
                .sellingStatus(sellingStatus)
                .name(name)
                .price(price)
                .build();
    }
}
