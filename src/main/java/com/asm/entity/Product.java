package com.asm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product implements Serializable {
    @Id
    private String product_id;
    private String product_name;
    private String product_description;
    private int product_price;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Categories category;

    @OneToMany(mappedBy="product_id")
    private List<Color> color;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;

    private boolean active;
}
