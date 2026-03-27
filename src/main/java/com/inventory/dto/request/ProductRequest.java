package com.inventory.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
    @NotBlank(message = "SKU is required")
    private String sku;

    @NotBlank(message = "Product name is required")
    private String name;

    private String description;

    @NotNull(message = "Unit price is required")
    @Positive(message = "Unit price must be positive")
    private BigDecimal unitPrice;

    @Positive(message = "Cost price must be positive")
    private BigDecimal costPrice;

    private Integer quantity = 0;
    private Integer reorderLevel = 10;
    private String imageUrl;
    private String barcode;
    private Long categoryId;
    private Long supplierId;
    private Long warehouseId;
}
