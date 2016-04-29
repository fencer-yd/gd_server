package com.ngj.product.impl;

import com.ngj.product.ProductService;
import com.ngj.product.mapper.ProductMapper;
import com.ngj.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by pangyueqiang on 16/4/25.
 */
@Component("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper mapper;

    public Long insert(Product product) {
        return mapper.insert(product);
    }

    public void update(Product product) {
        mapper.update(product);
    }

    public List<Product> findByTenant(Long tenant) {
        return mapper.findByTenant(tenant);
    }

    public void deleteById(Long id) {
        mapper.deleteById(id);
    }
}
