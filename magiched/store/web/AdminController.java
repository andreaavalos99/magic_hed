package com.magiched.store.web;

import com.magiched.store.domain.Product;
import com.magiched.store.repository.ProductRepository;
import com.magiched.store.service.CsvImportService;
import com.magiched.store.service.CsvImportService.ImportResult;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductRepository repo;
    private final CsvImportService csvImportService;

    public AdminController(ProductRepository repo, CsvImportService csvImportService) {
        this.repo = repo;
        this.csvImportService = csvImportService;
    }

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("products", repo.findAll());
        return "admin/index";
    }

    @GetMapping("/products/new")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product());
        return "admin/new-product";
    }

    @PostMapping("/products")
    public String create(@Valid @ModelAttribute("product") Product product,
                         BindingResult br,
                         @RequestParam(required = false) String priceArsStr) {

        if (product.getPriceArs() == null && priceArsStr != null && !priceArsStr.isBlank()) {
            try {
                product.setPriceArs(new BigDecimal(priceArsStr.replace(",", ".").trim()));
            } catch (Exception e) {
                br.rejectValue("priceArs", "invalid", "Precio inválido");
            }
        }

        if (br.hasErrors()) return "admin/new-product";

        repo.save(product);
        return "redirect:/admin";
    }

    // ✅ IMPORT CSV

    @GetMapping("/import")
    public String importForm() {
        return "admin/import";
    }

    @PostMapping("/import")
    public String importCsv(@RequestParam("file") MultipartFile file, Model model) {
        if (file == null || file.isEmpty()) {
            model.addAttribute("errors", java.util.List.of("Seleccioná un archivo CSV."));
            model.addAttribute("created", 0);
            return "admin/import-result";
        }

        try {
            ImportResult result = csvImportService.importProducts(file.getInputStream());
            model.addAttribute("created", result.created());
            model.addAttribute("errors", result.errors());
            return "admin/import-result";
        } catch (Exception e) {
            model.addAttribute("created", 0);
            model.addAttribute("errors", java.util.List.of("Error procesando archivo: " + e.getMessage()));
            return "admin/import-result";
        }
    }
}