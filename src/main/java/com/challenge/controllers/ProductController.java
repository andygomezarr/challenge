package com.challenge.controllers;

import com.challenge.entities.Product;
import com.challenge.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class ProductController {

    @Autowired
    private ProductService productService;

    // lista los productos
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/products")
    public ResponseEntity<List<Product>> listProducts() {
        try {
            return ResponseEntity.ok(productService.listProduct());
        } catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    // retorna los datos de solo un producto
    @RequestMapping(value = "{productId}")
    public ResponseEntity<Product> findProduct(@PathVariable("productId") Integer id) {
        try {
            return ResponseEntity.ok(productService.findById(id));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    // guarda un producto
    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        try {
            return ResponseEntity.ok(productService.save(product));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    /*
    @PutMapping
    public ResponseEntity<Product> editProduct(@RequestBody Product product) {
        try {
            Product newProduct = productService.findById(product.getId());
            if (product.getSKU() == 0) {
                newProduct.setCode(product.getCode());
            }
            newProduct.setName(product.getName());
            newProduct.setDescription(product.getDescription());
            newProduct.setPicture(product.getPicture());
            newProduct.setPrice(product.getPrice());
            newProduct.setCurrency(product.getCurrency());
            return ResponseEntity.ok(productService.save(newProduct));
        } catch (Exception exception) {
            return ResponseEntity.notFound().build();
        }
    }
     */

    // elimina un producto
    @DeleteMapping(value = "{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") Integer id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(null);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }
}
