package com.inventory.mapper;

import com.inventory.dto.response.CategoryResponse;
import com.inventory.dto.response.ProductResponse;
import com.inventory.entity.Category;
import com.inventory.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {
    public static CategoryResponse toCategoryResponse(Category category) {
        if (category == null) return null;
        CategoryResponse dto = new CategoryResponse();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setIcon(category.getIcon());
        if (category.getProducts() != null) {
            List<ProductResponse> products = category.getProducts().stream()
                .map(CategoryMapper::toProductResponse)
                .collect(Collectors.toList());
            dto.setProducts(products);
        }
        return dto;
    }

    public static ProductResponse toProductResponse(Product product) {
        if (product == null) return null;
        return ProductResponse.builder()
                .id(product.getId())
                .sku(product.getSku())
                .name(product.getName())
                .description(product.getDescription())
                .unitPrice(product.getUnitPrice())
                .costPrice(product.getCostPrice())
                .quantity(product.getQuantity())
                .reorderLevel(product.getReorderLevel())
                .imageUrl(product.getImageUrl())
                .barcode(product.getBarcode())
                .isActive(product.getIsActive())
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .supplierId(product.getSupplier() != null ? product.getSupplier().getId() : null)
                .supplierName(product.getSupplier() != null ? product.getSupplier().getCompanyName() : null)
                .warehouseId(product.getWarehouse() != null ? product.getWarehouse().getId() : null)
                .warehouseName(product.getWarehouse() != null ? product.getWarehouse().getName() : null)
                .build();
    }
}

