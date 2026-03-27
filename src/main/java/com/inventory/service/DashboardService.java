package com.inventory.service;

import com.inventory.dto.response.DashboardStats;
import com.inventory.entity.Category;
import com.inventory.entity.Product;
import com.inventory.entity.StockMovement;
import com.inventory.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final StockMovementRepository stockMovementRepository;

    public DashboardStats getStats() {
        long totalProducts = productRepository.countActiveProducts();
        List<Product> lowStock = productRepository.findLowStockProducts();
        BigDecimal totalValue = productRepository.getTotalInventoryValue();
        long totalCategories = categoryRepository.count();
        long totalSuppliers = supplierRepository.count();

        // Recent movements
        List<StockMovement> recentMovements = stockMovementRepository.findRecentMovements(PageRequest.of(0, 10));

        // Category breakdown
        List<Category> categories = categoryRepository.findAll();
        List<Map<String, Object>> categoryBreakdown = new ArrayList<>();
        for (Category cat : categories) {
            Map<String, Object> item = new HashMap<>();
            item.put("name", cat.getName());
            item.put("count", cat.getProducts() != null ? cat.getProducts().size() : 0);
            categoryBreakdown.add(item);
        }

        // Top products
        List<Product> topProducts = productRepository.findTopProducts(PageRequest.of(0, 10));
        List<Map<String, Object>> topProductsList = new ArrayList<>();
        for (Product p : topProducts) {
            Map<String, Object> item = new HashMap<>();
            item.put("name", p.getName());
            item.put("quantity", p.getQuantity());
            item.put("value", p.getUnitPrice() != null ? p.getUnitPrice().multiply(BigDecimal.valueOf(p.getQuantity())) : BigDecimal.ZERO);
            topProductsList.add(item);
        }

        // Recent activity
        List<Map<String, Object>> recentActivity = new ArrayList<>();
        for (StockMovement sm : recentMovements) {
            Map<String, Object> item = new HashMap<>();
            item.put("type", sm.getType().name());
            item.put("product", sm.getProduct().getName());
            item.put("quantity", sm.getQuantity());
            item.put("performedBy", sm.getPerformedBy());
            item.put("date", sm.getCreatedAt());
            recentActivity.add(item);
        }

        // Stock trend (last 30 days mock data — real implementation would query daily snapshots)
        List<Map<String, Object>> stockTrend = new ArrayList<>();
        for (int i = 29; i >= 0; i--) {
            Map<String, Object> point = new HashMap<>();
            point.put("date", LocalDateTime.now().minusDays(i).toLocalDate().toString());
            point.put("totalStock", totalProducts > 0 ? totalProducts + (new Random().nextInt(20) - 10) : 0);
            stockTrend.add(point);
        }

        return DashboardStats.builder()
                .totalProducts(totalProducts)
                .lowStockCount(lowStock.size())
                .totalInventoryValue(totalValue != null ? totalValue : BigDecimal.ZERO)
                .totalCategories(totalCategories)
                .totalSuppliers(totalSuppliers)
                .recentMovements(recentMovements.size())
                .stockTrend(stockTrend)
                .categoryBreakdown(categoryBreakdown)
                .topProducts(topProductsList)
                .recentActivity(recentActivity)
                .build();
    }
}
