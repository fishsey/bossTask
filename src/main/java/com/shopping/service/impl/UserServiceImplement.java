package com.shopping.service.impl;

import com.shopping.dao.UserDao;
import com.shopping.entity.User;
import com.shopping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 14437 on 2017/3/1.
 */
@Service
public class UserServiceImplement implements UserService
{

    @Autowired
    private UserDao userDao;


    @Override
    public User getUser(int id)
    {
        return userDao.getUser(id);
    }


    @Override
    public User getUser(String name)
    {

        return userDao.getUser(name);
    }

    @Override
    public void addUser(User user)
    {
        if (userDao.getUser(user.getName()) == null)
        {
            userDao.addUser(user);
        }
    }

}
