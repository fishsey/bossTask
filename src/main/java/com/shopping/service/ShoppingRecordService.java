package com.shopping.service;

import com.shopping.entity.ShoppingRecord;

import java.util.List;
import java.util.Set;

/**
 * Created by 14437 on 2017/3/3.
 */
public interface ShoppingRecordService {

    public void addShoppingRecord(ShoppingRecord shoppingRecord);

    public List<ShoppingRecord> getShoppingRecords(int userId);

    public Set<Integer> getShoppingProducts(int userId);

    public Set<Integer> getAllShoppingProducts();

    public Integer getshoppingCounts(int productId);

    public List<ShoppingRecord> getUserProductRecord(int userId,int productId);

}
