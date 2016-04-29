package com.ngj.product;

import com.ngj.product.model.Product;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by pangyueqiang on 16/4/25.
 */
public interface ProductService {
    Long insert(Product product);
    void update(Product product);
    List<Product> findByTenant(Long tenant);
    void deleteById(Long id);
}
