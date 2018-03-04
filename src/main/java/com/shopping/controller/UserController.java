package com.shopping.controller;

import com.shopping.entity.User;
import com.shopping.preProcess.Helper;
import com.shopping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 14437 on 2017/3/1.
 */
@Controller
public class UserController
{
    @Autowired
    UserService userService;

    @RequestMapping(value = "/register")
    public String register()
    {
        return "register";
    }

    @RequestMapping(value = "/login")
    public String login()
    {
        return "login";
    }

    @RequestMapping(value = "/main")
    public String main()
    {
        System.out.println("main");
        return "main";
    }

    @RequestMapping(value = "/control")
    public String control()
    {
        return "control";
    }


    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doLogin(String name, String password, HttpSession httpSession)
    {
        System.out.println("我接收到了登录请求" + name + " " + password);

        String result = "fail";
        User user = userService.getUser(name);

        System.out.println(user);

        if (user == null)
            result = "unexist";
        else
        {
            if (Helper.StringMD5(user.getPassword()).equalsIgnoreCase(password))
            {
                result = "success";
                httpSession.setAttribute("currentUser", user);
            } else
                result = "wrong";
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", result);
        return resultMap;
    }



    @RequestMapping(value = "/doLogout")
    public String doLogout(HttpSession httpSession)
    {
        //httpSession.setAttribute("currentUser","");
        httpSession.removeAttribute("currentUser");
        return "redirect:login";
    }

    @RequestMapping(value = "/doRegister", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doRegister(String userName, String nickName, String password, String phoneNumber)
    {
        System.out.println(userName);
        System.out.println(nickName);
        System.out.println(password);
        System.out.println(phoneNumber);

        String result = "fail";
        User user = userService.getUser(userName);
        System.out.println(user);

        if (user != null)
        {
            result = "nameExist";
        } else
        {

            User user1 = new User();

            user1.setName(userName);

            if (!nickName.equals(""))
                user1.setNickName(nickName);
            else
                user1.setNickName(userName);

            user1.setPassword(password);

            if (!phoneNumber.equals(""))
                user1.setPhoneNumber(phoneNumber);
            else
                user1.setPhoneNumber("null");
    
            System.out.println(user1);

            userService.addUser(user1);

            result = "success";
        }

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", result);
        return resultMap;
    }


}
