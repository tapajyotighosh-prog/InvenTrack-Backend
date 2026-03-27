package com.inventory.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PurchaseOrderRequest {
    @NotNull(message = "Supplier ID is required")
    private Long supplierId;

    private LocalDate expectedDate;
    private List<PurchaseOrderItemRequest> items;

    @Data
    public static class PurchaseOrderItemRequest {
        @NotNull private Long productId;
        @NotNull private Integer quantity;
        @NotNull private java.math.BigDecimal unitPrice;
    }
}
