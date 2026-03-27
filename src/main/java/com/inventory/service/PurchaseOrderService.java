package com.inventory.service;

import com.inventory.dto.request.PurchaseOrderRequest;
import com.inventory.entity.*;
import com.inventory.exception.ResourceNotFoundException;
import com.inventory.repository.ProductRepository;
import com.inventory.repository.PurchaseOrderRepository;
import com.inventory.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;

    public List<PurchaseOrder> getAllOrders() {
        return purchaseOrderRepository.findAll();
    }

    public PurchaseOrder getOrderById(Long id) {
        return purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PurchaseOrder", "id", id));
    }

    @Transactional
    public PurchaseOrder createOrder(PurchaseOrderRequest request) {
        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier", "id", request.getSupplierId()));

        PurchaseOrder order = PurchaseOrder.builder()
                .supplier(supplier)
                .orderDate(LocalDate.now())
                .expectedDate(request.getExpectedDate())
                .status(PurchaseOrder.OrderStatus.DRAFT)
                .build();

        BigDecimal total = BigDecimal.ZERO;
        if (request.getItems() != null) {
            for (PurchaseOrderRequest.PurchaseOrderItemRequest itemReq : request.getItems()) {
                Product product = productRepository.findById(itemReq.getProductId())
                        .orElseThrow(() -> new ResourceNotFoundException("Product", "id", itemReq.getProductId()));

                PurchaseOrderItem item = PurchaseOrderItem.builder()
                        .purchaseOrder(order)
                        .product(product)
                        .quantity(itemReq.getQuantity())
                        .unitPrice(itemReq.getUnitPrice())
                        .build();

                order.getItems().add(item);
                total = total.add(itemReq.getUnitPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity())));
            }
        }

        order.setTotalAmount(total);
        return purchaseOrderRepository.save(order);
    }

    @Transactional
    public PurchaseOrder updateStatus(Long id, PurchaseOrder.OrderStatus status) {
        PurchaseOrder order = getOrderById(id);
        order.setStatus(status);
        return purchaseOrderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(Long id) {
        PurchaseOrder order = getOrderById(id);
        purchaseOrderRepository.delete(order);
    }
}
