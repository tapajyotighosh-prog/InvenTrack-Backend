package com.inventory.service;

import com.inventory.dto.request.StockMovementRequest;
import com.inventory.entity.Product;
import com.inventory.entity.StockMovement;
import com.inventory.entity.Warehouse;
import com.inventory.exception.InsufficientStockException;
import com.inventory.exception.ResourceNotFoundException;
import com.inventory.repository.ProductRepository;
import com.inventory.repository.StockMovementRepository;
import com.inventory.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StockMovementService {

    private final StockMovementRepository stockMovementRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    @Transactional
    public StockMovement createMovement(StockMovementRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", request.getProductId()));

        Warehouse warehouse = null;
        if (request.getWarehouseId() != null) {
            warehouse = warehouseRepository.findById(request.getWarehouseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Warehouse", "id", request.getWarehouseId()));
        }

        switch (request.getType()) {
            case IN -> product.setQuantity(product.getQuantity() + request.getQuantity());
            case OUT -> {
                if (product.getQuantity() < request.getQuantity()) {
                    throw new InsufficientStockException(
                            "Insufficient stock for product '" + product.getName() +
                            "'. Available: " + product.getQuantity() + ", Requested: " + request.getQuantity());
                }
                product.setQuantity(product.getQuantity() - request.getQuantity());
            }
            case ADJUSTMENT -> product.setQuantity(request.getQuantity());
            case TRANSFER -> {
                if (product.getQuantity() < request.getQuantity()) {
                    throw new InsufficientStockException("Insufficient stock for transfer");
                }
                product.setQuantity(product.getQuantity() - request.getQuantity());
            }
        }

        productRepository.save(product);

        String currentUser = "system";
        try {
            currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception ignored) {}

        StockMovement movement = StockMovement.builder()
                .product(product)
                .warehouse(warehouse)
                .type(request.getType())
                .quantity(request.getQuantity())
                .referenceNo(request.getReferenceNo() != null ? request.getReferenceNo() : UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .notes(request.getNotes())
                .performedBy(currentUser)
                .build();

        return stockMovementRepository.save(movement);
    }

    public Page<StockMovement> getMovementsByProduct(Long productId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return stockMovementRepository.findByProductIdOrderByCreatedAtDesc(productId, pageable);
    }

    public List<StockMovement> getRecentMovements(int limit) {
        return stockMovementRepository.findRecentMovements(PageRequest.of(0, limit));
    }
}
