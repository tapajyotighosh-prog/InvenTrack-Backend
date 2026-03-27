package com.inventory.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "suppliers")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Supplier extends BaseEntity {

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "contact_name")
    private String contactName;

    @Column(unique = true)
    private String email;

    private String phone;

    private String address;

    @Builder.Default
    private Double rating = 0.0;

    @OneToMany(mappedBy = "supplier")
    @Builder.Default
    private List<Product> products = new ArrayList<>();
}
