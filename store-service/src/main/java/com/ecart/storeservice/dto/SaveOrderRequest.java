package com.ecart.storeservice.dto;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveOrderRequest {
    private Long userId;
    private Long productId;
    private Integer quantity;
}
