package com.inventory.controller;

import com.inventory.dto.request.PurchaseOrderRequest;
import com.inventory.dto.response.ApiResponse;
import com.inventory.entity.PurchaseOrder;
import com.inventory.service.PurchaseOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-orders")
@RequiredArgsConstructor
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PurchaseOrder>>> getAllOrders() {
        return ResponseEntity.ok(ApiResponse.success(purchaseOrderService.getAllOrders()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PurchaseOrder>> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(purchaseOrderService.getOrderById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PurchaseOrder>> createOrder(@Valid @RequestBody PurchaseOrderRequest request) {
        PurchaseOrder order = purchaseOrderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Order created", order));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<PurchaseOrder>> updateStatus(
            @PathVariable Long id, @RequestParam PurchaseOrder.OrderStatus status) {
        return ResponseEntity.ok(ApiResponse.success("Status updated", purchaseOrderService.updateStatus(id, status)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOrder(@PathVariable Long id) {
        purchaseOrderService.deleteOrder(id);
        return ResponseEntity.ok(ApiResponse.success("Order deleted", null));
    }
}
