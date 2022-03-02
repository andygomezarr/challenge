package com.challenge.services;

import com.challenge.dao.ProductDAO;
import com.challenge.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductDAO productDAO;

    // guarda el producto en la tabla
    @Transactional
    public Product save(Product product) throws Exception {
        try {
            validate(product);
            return productDAO.save(product);
        } catch (Exception exception) {
            throw exception;
        }
    }

    // entrega una listra de todos los productos
    public List<Product> listProduct() {
        try {
            return productDAO.findAll();
        } catch (Exception exception) {
            throw exception;
        }
    }

    // busca un producto por id
    public Product findById (Integer id) throws Exception {
        Optional<Product> answer = productDAO.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new Exception("ID no encontrado");
        }
    }

    // valida que el producto no tenga SKU, código, nombre o precio sea nulo o vacío
    private void validate(Product product) throws Exception{
        if (product.getSKU() == null || product.getSKU().equals(0)) {
            throw new Exception("El SKU no puede ser nulo o estar vacío");
        }
        if (product.getCode() == null || product.getCode().equals(0)) {
            throw new Exception("El código no puede ser nulo o estar vacío");
        }
        if (product.getName() == null || product.getName().equals("")) {
            throw new Exception("El nombre no puede ser nulo o estar vacío");
        }
        if (product.getPrice() == null || product.getPrice().equals(0D)) {
            throw new Exception("El precio no puede ser nulo o estar vacío");
        }
    }

    // elimina un producto
    @Transactional
    public void deleteProduct(Integer id) throws Exception {
        try {
            productDAO.delete(findById(id));
        } catch (Exception exception) {
            throw exception;
        }
    }
}
