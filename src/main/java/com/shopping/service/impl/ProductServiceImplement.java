package com.shopping.service.impl;

import com.shopping.dao.ProductDao;
import com.shopping.entity.Product;
import com.shopping.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 14437 on 2017/3/2.
 */

@Service
public class ProductServiceImplement implements ProductService
{

    @Autowired
    private ProductDao productDao;


    @Override
    public void addProduct(Product product)
    {
        productDao.addProduct(product);
    }
    @Override
    public void addProducts(List<Product> products)
    {
        productDao.addProducts(products);
    }

    @Override
    public Product getProduct(int id)
    {
        return productDao.getProduct(id);
    }

    @Override
    public Product getProduct(String name)
    {
        return productDao.getProduct(name);
    }


    @Override
    public List<Product> getAllProduct()
    {
        return productDao.getAllProduct();
    }


    @Override
    public boolean updateProductAllInfos(Product product)
    {
        return productDao.updateProductAllInfos(product);
    }

    @Override
    public boolean deleteProduct(int id)
    {
        return productDao.deleteProduct(id);
    }

    @Override
    public boolean updateProduct(Product product)
    {
        return productDao.updateProduct(product);
    }

    @Override
    public List<Product> getProductsByKeyWord(String searchKeyWord)
    {
        return productDao.getProductsByKeyWord(searchKeyWord);
    }

}
