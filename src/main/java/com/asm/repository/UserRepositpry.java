package com.asm.repository;

import com.asm.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositpry extends JpaRepository<Users,String> {
}
