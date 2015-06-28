/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.it250.projekat.dao;

import com.it250.projekat.entities.User;

/**
 *
 * @author Workbench
 */
public interface UserDao extends GenericDao{
    public User checkUser(String email, String password);
    public boolean checkEmail(String email);
    public boolean checkEmail(String email, int id);
    public boolean checkUsername(String username);
    public boolean checkUsername(String username, int id);
}
