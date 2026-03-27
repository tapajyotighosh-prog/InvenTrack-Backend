package com.inventory.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "warehouses")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Warehouse extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String location;

    private Integer capacity;

    @Column(name = "current_occupancy")
    @Builder.Default
    private Integer currentOccupancy = 0;
}
