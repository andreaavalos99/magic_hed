package com.magiched.store.web;

import com.magiched.store.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/store")
public class StoreController {

    private final ProductService productService;

    public StoreController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String storeHome(Model model) {
        model.addAttribute("products", productService.listActive(null));
        return "store/home";
    }

    @GetMapping("/products")
    public String list(@RequestParam(required = false) String q, Model model) {
        model.addAttribute("q", q);
        model.addAttribute("products", productService.listActive(q));
        return "store/products";
    }

    @GetMapping("/products/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getOrThrow(id));
        return "store/detail";
    }
}