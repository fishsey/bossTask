package com.shopping.preProcess;

import com.shopping.dao.ProductDao;
import com.shopping.dao.ShoppingCarDao;
import com.shopping.dao.ShoppingRecordDao;
import com.shopping.dao.UserDao;
import com.shopping.entity.Product;
import com.shopping.entity.ShoppingRecord;
import com.shopping.entity.User;
import com.shopping.service.ProductService;
import com.shopping.service.ShoppingRecordService;
import com.shopping.service.UserService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by fishsey on 2018/2/10.
 */
@Component
public class DataInjection
{
    @Autowired
    private ProductDao productdao;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserDao userdao;
    @Autowired
    private UserService userService;
    @Autowired
    private ShoppingCarDao shoppingCarDao;
    @Autowired
    private ShoppingRecordDao shoppingRecordDao;
    @Autowired
    private ShoppingRecordService shoppingRecordService;



    @PostConstruct
    public void  init() throws IOException, ParseException, InterruptedException
    {
        creteNewTableIfNotExist();
        injectionProductsData();
        injectionUsersData();
        injectionShoppingRecordData();
    }

    private void injectionShoppingRecordData() throws InterruptedException, ParseException
    {
        ShoppingRecord shoppingRecord = new ShoppingRecord();
        shoppingRecord.setUserId(1);
        shoppingRecord.setProductId(2);
        shoppingRecord.setProductPrice(30);
        shoppingRecord.setCounts(1);
        shoppingRecord.setOrderStatus(0);
        shoppingRecord.setTime("2018-03-04 10-49-12");
        shoppingRecordService.addShoppingRecord(shoppingRecord);

        shoppingRecord.setCounts(2);
        shoppingRecord.setProductPrice(60);
        shoppingRecord.setTime("2018-03-03 10-49-12");
        shoppingRecordService.addShoppingRecord(shoppingRecord);

        shoppingRecord.setTime("2018-03-02 10-49-12");
        shoppingRecord.setProductId(1);
        shoppingRecord.setCounts(1);
        shoppingRecord.setProductPrice(30);
        shoppingRecordService.addShoppingRecord(shoppingRecord);

    }


    private void creteNewTableIfNotExist()
    {
        productdao.createNewTable();
        userdao.createNewTable();
        shoppingCarDao.createNewTable();
        shoppingRecordDao.createNewTable();
    }

    public void injectionProductsData() throws IOException
    {
        //产品数据文件
        String[] fileNames = {"tempDatas/1-.csv", "tempDatas/2-.csv", "tempDatas/3-.csv"};

        for (String fileName : fileNames)
        {
            fileName = this.getClass().getResource("").toString().split("/classes")[0]  + "/classes/" + fileName;
            fileName = fileName.split("file:/+")[1];
            System.out.println(fileName);
            BufferedReader cin  = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"UTF-8"));

            ArrayList<Product> alst = new ArrayList<>();
            String line = null;
            while ((line = cin.readLine()) != null)
            {
                String[] fileds = line.split("\t");

                Product product = new Product();

                product.setCounts(100);
                product.setPrice(30);
                product.setType(Integer.valueOf(fileName.split("/")[fileName.split("/").length-1].split("-")[0]));
                product.setDescription(fileds[0]);

                if (product.getType()== TypeEnum.XH.getType())
                    product.setKeyWord(TypeEnum.XH.getTypeName());
                else if (product.getType()==TypeEnum.QH.getType())
                    product.setKeyWord(TypeEnum.QH.getTypeName());
                else if (product.getType()==TypeEnum.XX.getType())
                    product.setKeyWord(TypeEnum.XX.getTypeName());


                product.setPicPath(fileds[5].split("[.]")[0]);
                product.setName(fileds[fileds.length-1]);

                alst.add(product);
            }
            productService.addProducts(alst);
        }
    }

    private void injectionUsersData()
    {
        User u1 = new User();
        u1.setName("buyer");
        u1.setPassword("reyub");
        u1.setRole(1);
        u1.setPhoneNumber("null");
        u1.setNickName("buyer");

        User u2 = new User();
        u2.setName("seller");
        u2.setPassword("relles");
        u2.setRole(2);
        u2.setPhoneNumber("null");
        u2.setNickName("seller");

        userService.addUser(u1);
        userService.addUser(u2);
    }



}
