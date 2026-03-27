package com.inventory.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class DashboardStats {
    private long totalProducts;
    private long lowStockCount;
    private BigDecimal totalInventoryValue;
    private long totalCategories;
    private long totalSuppliers;
    private long recentMovements;
    private List<Map<String, Object>> stockTrend;
    private List<Map<String, Object>> categoryBreakdown;
    private List<Map<String, Object>> topProducts;
    private List<Map<String, Object>> recentActivity;
}
