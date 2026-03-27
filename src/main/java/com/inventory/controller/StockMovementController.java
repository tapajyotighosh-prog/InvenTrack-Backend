package com.inventory.controller;

import com.inventory.dto.request.StockMovementRequest;
import com.inventory.dto.response.ApiResponse;
import com.inventory.entity.StockMovement;
import com.inventory.service.StockMovementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
public class StockMovementController {

    private final StockMovementService stockMovementService;

    @PostMapping("/in")
    public ResponseEntity<ApiResponse<StockMovement>> stockIn(@Valid @RequestBody StockMovementRequest request) {
        request.setType(StockMovement.MovementType.IN);
        StockMovement movement = stockMovementService.createMovement(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Stock added", movement));
    }

    @PostMapping("/out")
    public ResponseEntity<ApiResponse<StockMovement>> stockOut(@Valid @RequestBody StockMovementRequest request) {
        request.setType(StockMovement.MovementType.OUT);
        StockMovement movement = stockMovementService.createMovement(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Stock removed", movement));
    }

    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse<StockMovement>> transfer(@Valid @RequestBody StockMovementRequest request) {
        request.setType(StockMovement.MovementType.TRANSFER);
        StockMovement movement = stockMovementService.createMovement(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Stock transferred", movement));
    }

    @PostMapping("/adjust")
    public ResponseEntity<ApiResponse<StockMovement>> adjust(@Valid @RequestBody StockMovementRequest request) {
        request.setType(StockMovement.MovementType.ADJUSTMENT);
        StockMovement movement = stockMovementService.createMovement(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Stock adjusted", movement));
    }

    @GetMapping("/history/{productId}")
    public ResponseEntity<ApiResponse<Page<StockMovement>>> getHistory(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<StockMovement> movements = stockMovementService.getMovementsByProduct(productId, page, size);
        return ResponseEntity.ok(ApiResponse.success(movements));
    }

    @GetMapping("/recent")
    public ResponseEntity<ApiResponse<List<StockMovement>>> getRecent(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(ApiResponse.success(stockMovementService.getRecentMovements(limit)));
    }
}
