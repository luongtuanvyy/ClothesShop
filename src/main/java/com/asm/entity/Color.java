package com.asm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "color")
public class Color implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String color_id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product_id;


    private String color_image;
    @OneToMany(mappedBy = "color")
    private List<OrderDetail> order_details_id;

    private String color_name;
    private boolean active;
}
