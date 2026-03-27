package com.inventory.dto.request;

import com.inventory.entity.StockMovement.MovementType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class StockMovementRequest {
    @NotNull(message = "Product ID is required")
    private Long productId;

    private Long warehouseId;

    @NotNull(message = "Movement type is required")
    private MovementType type;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    private String referenceNo;
    private String notes;
}
