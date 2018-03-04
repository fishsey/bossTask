package com.shopping.controller;

import com.alibaba.fastjson.JSONArray;
import com.shopping.entity.Product;
import com.shopping.entity.ShoppingRecord;
import com.shopping.service.ProductService;
import com.shopping.service.ShoppingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 14437 on 2017/3/3.
 */
@Controller
public class ShoppingRecordController
{
    @Autowired
    private ProductService productService;
    @Autowired
    private ShoppingRecordService shoppingRecordService;


    @RequestMapping(value = "/shopping_record")
    public String shopping_record()
    {
        return "shopping_record";
    }


    @RequestMapping(value = "/shopping_handle")
    public String shopping_handle()
    {
        return "shopping_handle";
    }


    @RequestMapping(value = "/addShoppingRecord", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addShoppingRecord(int userId, int productId, int counts)
    {
        System.out.println("我添加了" + userId + " " + productId);
        System.out.println(counts);
        String result = null;
        Product product = productService.getProduct(productId);
    
        System.out.println(product.getCounts());
        if (counts <= product.getCounts())
        {
            ShoppingRecord shoppingRecord = new ShoppingRecord();
            shoppingRecord.setUserId(userId);
            shoppingRecord.setProductId(productId);
            shoppingRecord.setProductPrice(product.getPrice() * counts);
            shoppingRecord.setCounts(counts);
            shoppingRecord.setOrderStatus(0);
            Date date = new Date();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            shoppingRecord.setTime(sf.format(date));

            product.setCounts(product.getCounts() - counts);
            productService.updateProduct(product);
            shoppingRecordService.addShoppingRecord(shoppingRecord);

            result = "success";
        } else
        {
            result = "unEnough";
        }
        System.out.println(result);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", result);
        return resultMap;
    }

    @RequestMapping(value = "/getShoppingRecords",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getShoppingRecords(int userId)
    {
        System.out.println(userId);
        List<ShoppingRecord> shoppingRecordList = shoppingRecordService.getShoppingRecords(userId);
        shoppingRecordList.forEach(System.out::println);

        String shoppingRecords = JSONArray.toJSONString(shoppingRecordList);
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("result", shoppingRecords);
        return resultMap;
    }

    @RequestMapping(value = "/getshoppingCounts", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getshoppingCounts(int productId)
    {
        System.out.println("getshoppingCounts");
        System.out.println(productId);
        Integer counts = shoppingRecordService.getshoppingCounts(productId);

        System.out.println("counts" + counts);

        String shoppingRecords = JSONArray.toJSONString(counts);
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("result", shoppingRecords);
        return resultMap;
    }

}
