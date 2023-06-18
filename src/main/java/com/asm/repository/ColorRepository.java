package com.asm.repository;

import com.asm.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ColorRepository extends JpaRepository<Color,String> {
    @Query("SELECT color FROM Color color WHERE color.product_id.product_id=:id AND color.color_name=:name")
    Color getColorByProductIdAndColorName(@Param("id") String id,@Param("name") String name);
}
