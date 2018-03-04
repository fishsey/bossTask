package temp;

import com.shopping.dao.UserDao;
import com.shopping.entity.ShoppingRecord;
import com.shopping.service.ProductService;
import com.shopping.service.ShoppingCarService;
import com.shopping.service.ShoppingRecordService;
import com.shopping.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by fishsey on 2018/2/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/applicationContext.xml"})
public class test
{
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;
    @Autowired
    ShoppingCarService shoppingCarService;
    @Autowired
    private ShoppingRecordService shoppingRecordService;

    @Autowired
    UserDao userDao;



    @Test
    public void test_2()
    {
        Integer productsId = shoppingRecordService.getshoppingCounts(3200489);
        System.out.println(productsId);
    }



    @Test
    public void test_()
    {

        //Product p1 = new Product();
        //Product p2 = new Product();
        //Product p3= new Product();
        //ArrayList<Product> alst = new ArrayList<>();
        //alst.add(p1);
        //alst.add(p2);
        //alst.add(p3);
        //
        //for (int i=0; i<=2; i++)
        //{
        //    alst.get(i).setPicPath(i+1 + "");
        //    alst.get(i).setName("book" + (i+1));
        //    alst.get(i).setDescription("book");
        //    alst.get(i).setKeyWord("book");
        //    alst.get(i).setPrice(i+1);
        //    alst.get(i).setCounts(i+1);
        //    alst.get(i).setType(i+1);
        //}
        //
        //productService.addProduct(alst.get(0));
        //productService.addProducts(alst.subList(1,3));

        productService.getAllProduct().forEach(System.out::println);


    }

    @Test
    public void test_3()
    {

        java.util.List<ShoppingRecord> alst = shoppingRecordService.getUserProductRecord(1, 2);

    }


}
