package com.shopping.dao;

import com.shopping.entity.Product;

import java.util.List;

/**
 * Created by 14437 on 2017/3/1.
 */
public interface ProductDao
{
    public void  createNewTable();

    public Product getProduct(int id);

    public List<Product> getProductsByKeyWord(String searchKeyWord);

    public List<Product> getAllProduct();

    public boolean updateProduct(Product product);
    public boolean updateProductAllInfos(Product product);


    public void addProduct(Product product);


    public void addProducts(List<Product> products);

    public boolean deleteProduct(int id);

    public Product getProduct(String name);



}
