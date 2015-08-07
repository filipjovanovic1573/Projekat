/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.it250.projekat.services;

import com.it250.projekat.dao.UserDao;
import com.it250.projekat.entities.User;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ValueEncoderFactory;

/**
 *
 * @author Workbench
 */
public class UserEncoder implements ValueEncoder<User>, ValueEncoderFactory<User>{
    
    @Inject
    private UserDao userDao;

    @Override
    public ValueEncoder<User> create(Class<User> type) {
       return this;
    }

    @Override
    public String toClient(User v) {
        return String.valueOf(v.getId());
    }

    @Override
    public User toValue(String string) {
        return userDao.getUserById(Integer.parseInt(string));
    }
    
}
