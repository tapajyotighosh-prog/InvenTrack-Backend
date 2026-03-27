package com.inventory.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class SupplierResponse {
    private Long id;
    private String companyName;
    private String contactName;
    private String email;
    private String phone;
    private String address;
    private Double rating;
    private List<ProductResponse> products;
}

