package com.asm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Orders implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String order_id;

    private String order_total;

    @Temporal(TemporalType.DATE)
    private Date order_date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    private boolean active;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

}
