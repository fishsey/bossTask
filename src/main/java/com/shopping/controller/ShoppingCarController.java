package com.shopping.controller;

import com.alibaba.fastjson.JSONArray;
import com.shopping.entity.Product;
import com.shopping.entity.ShoppingCar;
import com.shopping.entity.User;
import com.shopping.service.ProductService;
import com.shopping.service.ShoppingCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 14437 on 2017/3/3.
 */
@Controller
public class ShoppingCarController
{
    @Autowired
    private ShoppingCarService shoppingCarService;
    @Autowired
    private ProductService productService;


    @RequestMapping(value = "/shopping_car_Page", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> shopping_car_Page(String lastUrl, HttpSession httpSession)
    {
        System.out.println("shopping_car_Page");
        System.out.println(lastUrl);
        httpSession.setAttribute("lastPage", lastUrl);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", "success");
        return resultMap;
    }

    @RequestMapping(value = "/shopping_car")
    public String shopping_car()
    {
        return "shopping_car";
    }

    @RequestMapping(value = "/exitCar")
    public String exitCar(HttpSession httpSession)
    {
        String url =  httpSession.getAttribute("lastPage").toString();
        System.out.println(url);
        return "redirect:" + url.split(":[0-9]+")[1];
    }


    @RequestMapping(value = "/getShoppingCars", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getShoppingCars(int userId)
    {
        List<ShoppingCar> shoppingCarList = shoppingCarService.getShoppingCars(userId);
        String shoppingCars = JSONArray.toJSONString(shoppingCarList);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", shoppingCars);
        return resultMap;
    }

    @RequestMapping(value = "/addShoppingCar", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addShoppingCar(int productId, int counts, HttpSession httpSession)
    {
        System.out.println("数量为" + counts);
        Product product = productService.getProduct(productId);
        Map<String, Object> resultMap = new HashMap<String, Object>();

        User user = (User) httpSession.getAttribute("currentUser");

        if (counts <= product.getCounts())
        {
            ShoppingCar shoppingCar = shoppingCarService.getShoppingCar(user.getId(), productId);
            if (shoppingCar == null)
            {
                ShoppingCar shoppingCar1 = new ShoppingCar();
                shoppingCar1.setUserId(user.getId());
                shoppingCar1.setProductId(productId);
                shoppingCar1.setCounts(counts);
                shoppingCar1.setProductPrice(productService.getProduct(productId).getPrice() * counts);
                shoppingCarService.addShoppingCar(shoppingCar1);
            } else
            {
                shoppingCar.setCounts(shoppingCar.getCounts() + counts);
                shoppingCar.setProductPrice(productService.getProduct(productId).getPrice() * shoppingCar.getCounts());
                shoppingCarService.updateShoppingCar(shoppingCar);
            }

            resultMap.put("result", "success");
        }else
        {
            resultMap.put("result", "unEnough");
        }

        System.out.println("我返回了");
        return resultMap;
    }

    @RequestMapping(value = "/deleteShoppingCar",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> deleteShoppingCar(int userId,int productId)
    {
        shoppingCarService.deleteShoppingCar(userId, productId);
        Map<String, Object> resultMap = new HashMap<String,Object>();
        resultMap.put("result","success");
        System.out.println("我返回了");
        return resultMap;
    }


}
