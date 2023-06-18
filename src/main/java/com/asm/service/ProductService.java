package com.asm.service;

import com.asm.dto.ProductDTO;
import com.asm.dto.Statistical;
import com.asm.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Statistical> getCateStatisticals();
    List<ProductDTO> getProductByCategoryName(String category_name,String sortType);
    ProductDTO getProductById(String id, String color);
    List<ProductDTO> removeCurrentProduct(ProductDTO productDTO);
    void saveProductDTO(String productId, String productColor);
    void setActiveProduct(String productId, String productColor);

    Optional<Product> getProductById(String productId);
}
