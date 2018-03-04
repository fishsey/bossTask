package com.shopping.service.impl;

import com.shopping.dao.ShoppingRecordDao;
import com.shopping.entity.ShoppingRecord;
import com.shopping.service.ShoppingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created by 14437 on 2017/3/3.
 */
@Service
public class ShoppingRecordServiceImplement implements ShoppingRecordService
{
    @Autowired
    private ShoppingRecordDao shoppingRecordDao;

    @Override
    public void addShoppingRecord(ShoppingRecord shoppingRecord)
    {
        shoppingRecordDao.addShoppingRecord(shoppingRecord);
    }

    @Override
    public List<ShoppingRecord> getShoppingRecords(int userId)
    {
        return shoppingRecordDao.getShoppingRecords(userId);
    }

    @Override
    public Set<Integer> getShoppingProducts(int userId)
    {
        return shoppingRecordDao.getShoppingProducts(userId);
    }

    @Override
    public Set<Integer> getAllShoppingProducts()
    {
        return shoppingRecordDao.getAllShoppingProducts();
    }


    @Override
    public Integer getshoppingCounts(int productId)
    {
        return shoppingRecordDao.getshoppingCounts(productId);
    }

    @Override
    public List<ShoppingRecord> getUserProductRecord(int userId, int productId)
    {
        return shoppingRecordDao.getUserProductRecord(userId, productId);
    }
}
