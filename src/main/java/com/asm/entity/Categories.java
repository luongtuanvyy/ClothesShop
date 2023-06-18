package com.asm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Categories implements Serializable {
    @Id
    private String category_id;

    private String category_name;

    private boolean active;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> list_products;
}
