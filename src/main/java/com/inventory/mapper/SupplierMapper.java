package com.inventory.mapper;

import com.inventory.dto.response.SupplierResponse;
import com.inventory.dto.response.ProductResponse;
import com.inventory.entity.Supplier;
import com.inventory.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

public class SupplierMapper {
    public static SupplierResponse toSupplierResponse(Supplier supplier) {
        if (supplier == null) return null;
        SupplierResponse dto = new SupplierResponse();
        dto.setId(supplier.getId());
        dto.setCompanyName(supplier.getCompanyName());
        dto.setContactName(supplier.getContactName());
        dto.setEmail(supplier.getEmail());
        dto.setPhone(supplier.getPhone());
        dto.setAddress(supplier.getAddress());
        dto.setRating(supplier.getRating());
        if (supplier.getProducts() != null) {
            List<ProductResponse> products = supplier.getProducts().stream()
                .map(SupplierMapper::toProductResponse)
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
                .warehouseId(product.getWarehouse() != null ? product.getWarehouse().getId() : null)
                .warehouseName(product.getWarehouse() != null ? product.getWarehouse().getName() : null)
                .build();
    }
}

