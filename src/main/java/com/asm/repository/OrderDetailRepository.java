package com.asm.repository;

import com.asm.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {
    @Query("SELECT SUM(od.quantity) FROM OrderDetail od WHERE od.product.product_id=:Id")
    int getTotalQuantityWithProductID(String Id);
}
