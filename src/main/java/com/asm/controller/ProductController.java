package com.asm.controller;

import com.asm.dto.ProductDTO;
import com.asm.dto.Statistical;
import com.asm.entity.OrderDetail;
import com.asm.entity.Orders;
import com.asm.entity.Product;
import com.asm.entity.Users;
import com.asm.repository.ColorRepository;
import com.asm.repository.OrderDetailRepository;
import com.asm.service.CategoryService;
import com.asm.service.ColorService;
import com.asm.service.ProductService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    ColorService colorService;
    @Autowired
    private Cloudinary cloudinary;

    @GetMapping(value = {"/all", "/top", "/suiting", "footwear", "bottom"})
    public String showProducts(Model model, HttpServletRequest request, @RequestParam(value = "sort", defaultValue = "none") String sort, @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber) {

        String categoryName = String.valueOf(request.getRequestURI()).substring(9);

        List<Statistical> listStatistical = productService.getCateStatisticals();
        List<ProductDTO> listProducts = productService.getProductByCategoryName(categoryName, String.valueOf(sort));
        List<Integer> listPage = IntStream.rangeClosed(1, (int) Math.ceil(listStatistical.size() / 9)).boxed().toList();

        model.addAttribute("listCategory", listStatistical);
        model.addAttribute("listSales", "");
        model.addAttribute("page", categoryName);
        model.addAttribute("listProduct", listProducts);
        model.addAttribute("listPage", listPage);
        model.addAttribute("sort", sort);

        return "user/products";
    }

    @GetMapping("")
    public String showProduct(Model model, @RequestParam(value = "id") String id, @RequestParam(value = "color") String color) {
        ProductDTO productDTO = productService.getProductById(id, color);
        model.addAttribute("product", productDTO);
        model.addAttribute("listRelateTo", productService.removeCurrentProduct(productDTO));
        return "user/detail";
    }

    @PostMapping("/addToCart")
    public RedirectView addToCart(Model model,
                          HttpServletRequest request,
                          HttpSession session,
                          @RequestParam(value = "id") String id,
                          @RequestParam(value = "color") String color,
                          @RequestParam(value = "size") String size,
                          @RequestParam(value = "quantity") String quantity
    ){
        if(session.getAttribute("order")==null){
            Orders order = new Orders();
            order.setOrder_date(new Date());
            order.setUsers((Users) session.getAttribute("user"));
            order.setActive(true);

            session.setAttribute("order", order);
        }
        Orders order = (Orders) session.getAttribute("order");
        OrderDetail detail = new OrderDetail();
                    detail.setOrder(order);
                    detail.setProduct(productService.getProductById(id).get());
                    detail.setProduct_size(size);
                    detail.setColor(colorService.getColorByProductIdAndColorName(id,color));
                    detail.setQuantity(Integer.parseInt(quantity));
        return new RedirectView("/product/?id="+id+"&color="+color);
    }

    @GetMapping("/admin")
    public String showProduct(Model model) {
        model.addAttribute("productUpdate", new ProductDTO());
        model.addAttribute("listProduct", productService.getProductByCategoryName("all", "none"));
        model.addAttribute("listCategory", categoryService.getCategoryName());
        return "/admin/product";
    }

    @PostMapping("/admin/update")
    public RedirectView update(Model model, @ModelAttribute("productDTO") ProductDTO productDTO, @RequestParam(value = "id", defaultValue = "P001") String productId, @RequestParam(value = "fileImage", required = false) MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            Map map = this.cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
            String urlImage = (String) map.get("url");
        }
        productService.getProductById(productDTO.getProduct_id(), productDTO.getColor_name());

        return new RedirectView("/product/admin");
    }

    @PostMapping("/admin/active")
    public RedirectView setActiveProduct(@RequestParam(value = "id") String productId, @RequestParam(value="color") String productColor) {
//        productService.saveProductDTO("P001", "Gray");
        productService.setActiveProduct(productId, productColor);
        return new RedirectView("/product/admin");
    }
}
