package com.asm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "users")
public class Users implements Serializable {
    @Id
    private String user_name;

    private String user_email;

    private String user_password;

    private String user_address;

    private boolean user_role;

    private boolean active;

    @OneToMany(mappedBy = "users")
    private Collection<Orders> orders;
}
