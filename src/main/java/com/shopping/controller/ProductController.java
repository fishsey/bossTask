package com.shopping.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.shopping.entity.Product;
import com.shopping.entity.ShoppingRecord;
import com.shopping.entity.User;
import com.shopping.preProcess.TypeEnum;
import com.shopping.service.ProductService;
import com.shopping.service.ShoppingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

/**
 * Created by 14437 on 2017/3/1.
 */
@Controller
public class ProductController
{
    @Autowired
    private ProductService productService;

    @Autowired
    private ShoppingRecordService shoppingRecordService;


    @RequestMapping(value = "/getAllProducts", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getAllProducts(String unPurchase, HttpSession httpSession)
    {
        System.out.println("getAllProducts");
        System.out.println(unPurchase);
        System.out.println(httpSession.getAttribute("currentUser"));

        Set<Integer> productsId = new HashSet<>();

        if (unPurchase.equals("true"))
        {
            User user = (User) httpSession.getAttribute("currentUser");
            productsId = shoppingRecordService.getShoppingProducts(user.getId());
        }

        System.out.println(productsId);

        List<Product> productListTemp = new ArrayList<>();
        List<Product> productList = new ArrayList<>();

        productListTemp = productService.getAllProduct();
        for (Product product : productListTemp)
        {
            if (!productsId.contains(product.getId()))
            {
                productList.add(product);
            }
        }
        System.out.println(productListTemp.size());
        System.out.println(productList.size());

        String allProducts = JSONArray.toJSONString(productList);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("allProducts", allProducts);
        return resultMap;
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addProduct(String name, String description, int type, int price, int counts, HttpSession httpSession)
    {
        System.out.println("添加了商品：" + name);
        System.out.println(type);
        String result = "fail";

        Product product = new Product();

        product.setName(name);
        product.setDescription(description);
        product.setType(type);
        if (product.getType()== TypeEnum.XH.getType())
            product.setKeyWord(TypeEnum.XH.getTypeName());
        else if (product.getType()==TypeEnum.QH.getType())
            product.setKeyWord(TypeEnum.QH.getTypeName());
        else if (product.getType()==TypeEnum.XX.getType())
            product.setKeyWord(TypeEnum.XX.getTypeName());
        product.setPrice(price);
        product.setCounts(counts);


        httpSession.setAttribute("tempProduct", product);

        result = "success";
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", result);

        System.out.println(product);
        return resultMap;
    }

    @RequestMapping(value = "/uploadFileAdd", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadFileAdd(@RequestParam MultipartFile productImgUpload, String name, HttpServletRequest request, HttpSession httpSession)
    {
        System.out.println("uploadFile");

        String result = "fail";
        Product product = (Product) httpSession.getAttribute("tempProduct");
        try
        {
            if (product!=null && productImgUpload != null && !productImgUpload.isEmpty())
            {
                String fileRealPath = request.getSession().getServletContext().getRealPath("WEB-INF/statics/img");
                File fileFolder = new File(fileRealPath);

                String picPath = String.valueOf(name.hashCode()) + Math.round(Math.random());
                String fileName = picPath + ".jpg";

                System.out.println("fileRealPath=" + fileRealPath + "\t" + fileName);


                if (!fileFolder.exists())
                {
                    fileFolder.mkdirs();
                }
                File file = new File(fileFolder, fileName);
                productImgUpload.transferTo(file);


                product.setPicPath(picPath);

                productService.addProduct(product);
                System.out.println(product);

                httpSession.removeAttribute("tempProduct");
                httpSession.setAttribute("productDetail", product);

                result = "success";
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", result);
        return resultMap;
    }


    @RequestMapping(value = "/editProduct", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editProduct(String name, String description, int type, int price, int counts, HttpSession httpSession)
    {
        System.out.println("修改了商品：" + name);
        System.out.println(type);
        String result = "fail";

        Product product = (Product) httpSession.getAttribute("productDetail");

        product.setName(name);
        product.setDescription(description);
        product.setType(type);
        if (product.getType()== TypeEnum.XH.getType())
            product.setKeyWord(TypeEnum.XH.getTypeName());
        else if (product.getType()==TypeEnum.QH.getType())
            product.setKeyWord(TypeEnum.QH.getTypeName());
        else if (product.getType()==TypeEnum.XX.getType())
            product.setKeyWord(TypeEnum.XX.getTypeName());
        product.setPrice(price);
        product.setCounts(counts);

        productService.updateProductAllInfos(product);
        httpSession.setAttribute("productDetail", product);

        result = "success";
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", result);

        System.out.println(product);
        return resultMap;
    }

    @RequestMapping(value = "/uploadFileEdit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadFileEdit(@RequestParam MultipartFile productImgUpload, String name, HttpServletRequest request, HttpSession httpSession)
    {
        System.out.println("uploadFile");

        String result = "fail";
        Product product = (Product) httpSession.getAttribute("productDetail");
        try
        {
            if (product!=null && productImgUpload != null && !productImgUpload.isEmpty())
            {
                String fileRealPath = request.getSession().getServletContext().getRealPath("WEB-INF/statics/img");
                File fileFolder = new File(fileRealPath);

                String picPath = String.valueOf(name.hashCode()) + Math.round(Math.random());
                String fileName = picPath + ".jpg";

                System.out.println("fileRealPath=" + fileRealPath + "\t" + fileName);


                if (!fileFolder.exists())
                {
                    fileFolder.mkdirs();
                }
                File file = new File(fileFolder, fileName);
                productImgUpload.transferTo(file);


                product.setPicPath(picPath);
                productService.updateProductAllInfos(product);
                System.out.println(product);
                httpSession.setAttribute("productDetail", product);

                result = "success";
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", result);
        return resultMap;
    }


    
    @RequestMapping(value = "/getAllPurchaseProducts", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getAllPurchaseProducts(HttpSession httpSession)
    {
        Set<Integer> productsId = new HashSet<>();
        productsId = shoppingRecordService.getAllShoppingProducts();
        System.out.println("getAllPurchaseProducts");
        System.out.println(productsId);
        String allProducts = JSONArray.toJSONString(productsId);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("allProducts", allProducts);
        return resultMap;
    }


    @RequestMapping(value = "/getProductById", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getProductById(int id)
    {
        Product product = productService.getProduct(id);
        String result = JSON.toJSONString(product);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", result);
        return resultMap;
    }

    @RequestMapping(value = "/productDetail", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> productDetail(String id, HttpSession httpSession)
    {
        System.out.println("I am here!" + id);

        Product product = productService.getProduct(id);
        httpSession.setAttribute("productDetail", product);

        System.out.print("I am here" + product.getName());

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", "success");
        return resultMap;
    }

    @RequestMapping(value = "/product_detail")
    public String product_detail()
    {
        return "product_detail";
    }


    @RequestMapping(value = "/searchPre", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> searchPre(String searchKeyWord, HttpSession httpSession)
    {
        httpSession.setAttribute("searchKeyWord", searchKeyWord);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", "success");
        return resultMap;
    }

    @RequestMapping(value = "/search")
    public String search()
    {
        return "search";
    }

    @RequestMapping(value = "/searchProduct", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> searchProduct(String searchKeyWord)
    {
        System.out.println("我到了SearchProduct" + searchKeyWord);

        List<Product> productList = new ArrayList<Product>();
        productList = productService.getProductsByKeyWord(searchKeyWord);

        String searchResult = JSONArray.toJSONString(productList);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", searchResult);

        System.out.println("我返回了" + searchResult);
        return resultMap;
    }

    @RequestMapping(value = "/deleteProduct", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteProduct(int productId)
    {
        System.out.println("deleteProduct");
        String result = "fail";
        if (productService.deleteProduct(productId))
        {
            result = "success";
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", result);
        return resultMap;
    }

    @RequestMapping(value = "/isPurchase", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> isPurchase(int productId, HttpSession httpSession)
    {
        System.out.println("isPurchase");

        String allrecords = "no";

        Set<Integer> productsId = new HashSet<>();
        User user = (User) httpSession.getAttribute("currentUser");
        if (user != null)
        {
            List<ShoppingRecord> result = shoppingRecordService.getUserProductRecord(user.getId(), productId);
            System.out.println(result);

            if (result != null && result.size() > 0)
            {
                allrecords = JSONArray.toJSONString(result);
            }
        }
        System.out.println(allrecords);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", allrecords);
        return resultMap;
    }


}
