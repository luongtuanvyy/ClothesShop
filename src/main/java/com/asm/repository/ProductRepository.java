package com.asm.repository;

import com.asm.dto.Statistical;
import com.asm.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    @Query("SELECT new Statistical (cate.category_name,count(pro.product_id)) FROM Categories cate join Product pro on cate.category_id = pro.category.category_id GROUP BY cate.category_name ORDER BY cate.category_name DESC")
    List<Statistical> countItem();

    @Query("SELECT pro FROM Product pro JOIN Categories cate ON pro.category.category_id = cate.category_id WHERE cate.category_name = :name")
    List<Product> findAllByCategoryName(@Param("name") String name);

    @Query("SELECT pro FROM Product pro JOIN Color clo ON pro.product_id = clo.product_id.product_id WHERE clo.color_name =:color_name AND pro.product_id=:id")
    Product findByIdAndColorName(@Param("id") String id, @Param("color_name") String colorName);
}
