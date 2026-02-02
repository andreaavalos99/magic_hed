package com.magiched.store.service;

import com.magiched.store.domain.Product;
import com.magiched.store.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class CsvImportService {

    private final ProductRepository repo;

    public CsvImportService(ProductRepository repo) {
        this.repo = repo;
    }

    public ImportResult importProducts(InputStream csvStream) {
        List<String> errors = new ArrayList<>();
        int created = 0;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(csvStream, StandardCharsets.UTF_8))) {
            String headerLine = br.readLine();
            if (headerLine == null) return new ImportResult(0, List.of("El CSV está vacío"));

            List<String> headers = splitCsvLine(headerLine);
            Map<String, Integer> idx = headerIndex(headers);

            // Validar headers mínimos
            List<String> required = List.of("name", "description", "priceArs", "imageUrl", "preorder", "productionNote", "active");
            for (String r : required) {
                if (!idx.containsKey(r)) errors.add("Falta columna obligatoria: " + r);
            }
            if (!errors.isEmpty()) return new ImportResult(0, errors);

            String line;
            int row = 1; // header = 1
            while ((line = br.readLine()) != null) {
                row++;
                if (line.isBlank()) continue;

                List<String> cols = splitCsvLine(line);

                try {
                    Product p = new Product();
                    p.setName(get(cols, idx.get("name")));
                    p.setDescription(get(cols, idx.get("description")));
                    p.setImageUrl(get(cols, idx.get("imageUrl")));

                    String priceRaw = get(cols, idx.get("priceArs")).replace(",", ".").trim();
                    p.setPriceArs(new BigDecimal(priceRaw));

                    p.setPreorder(parseBool(get(cols, idx.get("preorder"))));
                    p.setProductionNote(get(cols, idx.get("productionNote")));
                    p.setActive(parseBool(get(cols, idx.get("active"))));

                    // Validaciones mínimas (sin tirar toda la importación)
                    if (p.getName() == null || p.getName().isBlank()) throw new IllegalArgumentException("name vacío");
                    if (p.getImageUrl() == null || p.getImageUrl().isBlank()) throw new IllegalArgumentException("imageUrl vacío");

                    repo.save(p);
                    created++;
                } catch (Exception e) {
                    errors.add("Fila " + row + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            errors.add("Error leyendo CSV: " + e.getMessage());
        }

        return new ImportResult(created, errors);
    }

    private static Map<String, Integer> headerIndex(List<String> headers) {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < headers.size(); i++) {
            map.put(headers.get(i).trim(), i);
        }
        return map;
    }

    private static String get(List<String> cols, int i) {
        if (i < 0 || i >= cols.size()) return "";
        return cols.get(i);
    }

    private static boolean parseBool(String v) {
        if (v == null) return false;
        String s = v.trim().toLowerCase();
        return s.equals("true") || s.equals("1") || s.equals("si") || s.equals("sí") || s.equals("yes");
    }

    // Split CSV simple con soporte básico de comillas
    private static List<String> splitCsvLine(String line) {
        List<String> out = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes;
                continue;
            }
            if (c == ',' && !inQuotes) {
                out.add(cur.toString().trim());
                cur.setLength(0);
                continue;
            }
            cur.append(c);
        }
        out.add(cur.toString().trim());
        return out;
    }

    public record ImportResult(int created, List<String> errors) {}
}