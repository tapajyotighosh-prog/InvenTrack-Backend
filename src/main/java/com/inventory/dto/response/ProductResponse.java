package com.inventory.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String sku;
    private String name;
    private String description;
    private BigDecimal unitPrice;
    private BigDecimal costPrice;
    private Integer quantity;
    private Integer reorderLevel;
    private String imageUrl;
    private String barcode;
    private Boolean isActive;
    private String categoryName;
    private Long categoryId;
    private String supplierName;
    private Long supplierId;
    private String warehouseName;
    private Long warehouseId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
