package com.shopping.dao;

import com.shopping.entity.User;

/**
 * Created by 14437 on 2017/3/1.
 */
public interface UserDao
{
    public void  createNewTable();

    public User getUser(int id);
    public User getUser(String name);

    public void addUser(User user);

}
