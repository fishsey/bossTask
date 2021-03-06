package com.shopping.dao;

import com.shopping.entity.ShoppingCar;

import java.util.List;

/**
 * Created by 14437 on 2017/3/3.
 */
public interface ShoppingCarDao
{
    public void  createNewTable();


    public List<ShoppingCar> getShoppingCars(int userId);


    public ShoppingCar getShoppingCar(int userId, int productId);


    public void addShoppingCar(ShoppingCar shoppingCar);


    public boolean updateShoppingCar(ShoppingCar shoppingCar);


    public boolean deleteShoppingCar(int userId, int productId);
}
