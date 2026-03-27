package com.inventory.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;
    private String icon;
    private List<ProductResponse> products;
}

