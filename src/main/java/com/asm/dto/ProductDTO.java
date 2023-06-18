package com.asm.dto;

import com.asm.entity.Color;
import com.asm.entity.OrderDetail;
import com.asm.entity.Product;
import com.asm.repository.OrderDetailRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@NoArgsConstructor
@Getter
public class ProductDTO implements Serializable {
    private String product_id;
    private String product_name;
    private String product_description;
    private int product_price;
    private boolean active;
    private String color_image;
    private String color_name;
    private String color_id;
    private String category_name;
    private int countProductUserBuy;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    public ProductDTO(Product product, Color color) {
        this.product_id = product.getProduct_id();
        this.product_name = product.getProduct_name();
        this.product_description = product.getProduct_description();
        this.product_price = product.getProduct_price();
        this.active = color.isActive();
        this.color_name = color.getColor_name();
        this.color_image = color.getColor_image();
        this.color_id = color.getColor_id();
        this.category_name = product.getCategory().getCategory_name();
    }

    public List<ProductDTO> setProductDTO(List<Product> products) {
        List<ProductDTO> listProductDTO = new ArrayList<ProductDTO>();
        for (Product product : products) {
            for (Color color : product.getColor()) {
                listProductDTO.add(new ProductDTO(product, color));
            }
        }
        return listProductDTO;
    }
}
