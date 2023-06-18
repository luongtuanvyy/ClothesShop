package com.asm.service.serviceImpl;

import com.asm.dto.ProductDTO;
import com.asm.dto.Statistical;
import com.asm.entity.Color;
import com.asm.entity.Product;
import com.asm.repository.ColorRepository;
import com.asm.repository.OrderDetailRepository;
import com.asm.repository.ProductRepository;
import com.asm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImplement implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    ColorRepository colorRepository;
    ProductDTO productDTO = new ProductDTO();

    @Override
    public List<Statistical> getCateStatisticals() {
        List<Statistical> statis = productRepository.countItem();
        long count = 0;
        for (Statistical sta : statis) {
            count += sta.getCount();
        }
        statis.add(new Statistical("All", count));
        Collections.sort(statis, new Comparator<Statistical>() {
            @Override
            public int compare(Statistical o1, Statistical o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        return statis;
    }

    @Override
    public List<ProductDTO> getProductByCategoryName(String category_name, String sortType) {
        List<ProductDTO> products = productDTO.setProductDTO(productRepository.findAll());
        if (category_name.equals("all")) {
            products = productDTO.setProductDTO(productRepository.findAll());
        } else {
            products = productDTO.setProductDTO(productRepository.findAllByCategoryName(category_name));
        }
        for (ProductDTO productDTO : products) {
            productDTO.setCountProductUserBuy(orderDetailRepository.getTotalQuantityWithProductID(productDTO.getProduct_id()));
        }
        switch (sortType) {
            case "popular":
                Collections.sort(products, new Comparator<ProductDTO>() {
                    @Override
                    public int compare(ProductDTO o1, ProductDTO o2) {
                        return o1.getCountProductUserBuy() - o2.getCountProductUserBuy();
                    }
                });
                break;
            case "name":
                Collections.sort(products, new Comparator<ProductDTO>() {
                    @Override
                    public int compare(ProductDTO o1, ProductDTO o2) {
                        return o1.getProduct_name().compareTo(o2.getProduct_name());
                    }
                });
                break;
            default:
                break;
        }
        return products;

    }

    @Override
    public ProductDTO getProductById(String id, String color) {
        Optional<Product> product = productRepository.findById(id);
        ProductDTO productDTO = new ProductDTO();
        for (Color co : productRepository.findById(id).get().getColor()) {
            if (color.equalsIgnoreCase(co.getColor_name())) {
                productDTO = new ProductDTO(product.get(), co);
            }
        }
        return productDTO;
    }

    @Override
    public List<ProductDTO> removeCurrentProduct(ProductDTO productDTO) {
        List<ProductDTO> listOther = getProductByCategoryName(productDTO.getCategory_name(), "none");

        for (int i = 0; i < listOther.size(); i++) {
            if (listOther.get(i).getProduct_id().equals(productDTO.getProduct_id()) && listOther.get(i).getColor_name().equals(productDTO.getColor_name())) {
                listOther.remove(i);
            }
        }
        return listOther;
    }
    @Override
    public void setActiveProduct(String productId, String productColor) {
        Optional<Product> product = productRepository.findById(productId);
        String colorId = "";

        for (Color col : product.get().getColor()) {
            if (col.getColor_name().equalsIgnoreCase(productColor)) {
               colorId = col.getColor_id();
            }
        }

        Optional<Color> color = colorRepository.findById(colorId);
        boolean active = !color.get().isActive();
        color.get().setActive(active);
        colorRepository.save(color.get());
    }

    @Override
    public Optional<Product> getProductById(String productId) {
        return productRepository.findById(productId);
    }

    @Override
    public void saveProductDTO(String productId, String productColor) {
        Optional<Product> product = productRepository.findById(productId);
        String colorId = "";
        for (Color color : product.get().getColor()) {
            if (color.getColor_name().equalsIgnoreCase(productColor)) {
                colorId = color.getColor_id();
            }
        }
        Optional<Color> color = colorRepository.findById(colorId);
        color.get().setColor_name("Blue");
        if(color.isEmpty()){
            return ;
        }
        colorRepository.save(color.get());
    }
}
