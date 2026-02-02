package com.magiched.store.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Size(max = 2000)
    private String description;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal priceArs;

    @NotBlank
    private String imageUrl;

    private boolean active = true;

    private boolean preorder = false;
    private String productionNote;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPriceArs() { return priceArs; }
    public void setPriceArs(BigDecimal priceArs) { this.priceArs = priceArs; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public boolean isPreorder() { return preorder; }
    public void setPreorder(boolean preorder) { this.preorder = preorder; }

    public String getProductionNote() { return productionNote; }
    public void setProductionNote(String productionNote) { this.productionNote = productionNote; }
}