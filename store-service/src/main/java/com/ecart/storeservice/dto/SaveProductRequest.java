package com.ecart.storeservice.dto;



import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveProductRequest {


    private Long id;

    @NotEmpty(message = "Ürün ismi boş olamaz")
    private String name;

    @NotEmpty(message = "Açıklama boş olamaz")
    private String description;

    @NotNull(message = "Fiyat belirtilmelidir")
    private Double price;

    @NotNull(message = "Stok belirtilmelidir")
    private Integer stock;
}
