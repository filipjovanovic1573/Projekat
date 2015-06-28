/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.it250.projekat.pages;

import com.it250.projekat.dao.UserDao;
import com.it250.projekat.other.TrashHash;
import com.it250.projekat.entities.User;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;

/**
 *
 * @author Workbench
 */
// @Import(stylesheet="context:/css/auxiliary.css")
public class Register {

    //<editor-fold defaultstate="collapsed" desc="Properties and annotations">

    @Property
    private User user;

    @Inject
    AlertManager alert;

    @InjectComponent
    private BeanEditForm register;

    @InjectComponent
    private PasswordField confirmPassword;

    @InjectComponent
    private TextField username, mail;

    @Property
    private ArrayList<User> users;

    @Property
    @Validate("regexp=^[A-Za-z ]+$")
    private String firstnameValue, lastnameValue;

    @Property
    @Validate("required, regexp=^[A-Za-z0-9-_]+$")
    private String usernameValue;

    @Property
    @Validate("required, regexp=^[A-Za-z0-9-_<>]+$")
    private String passwordValue, confpasswordValue;

    @Property
    @Validate("required, email")
    private String emailValue;

    private String userFolder = "E:\\IT250-Projekat\\Users\\";
    
    @Inject
    private UserDao userDao;
    //</editor-fold>

    void onActivate() {
        if (users == null) {
            users = new ArrayList<User>();
        }

        users = (ArrayList<User>) userDao.findAll(User.class);
    }

    void onValidateFromRegister() {
        if (!passwordValue.equals(confpasswordValue)) {
            register.recordError(confirmPassword, "Passwords don't match");
        }
        
        if(!userDao.checkEmail(emailValue)){
            register.recordError(mail, "Email is taken");
        }
        
        if(!userDao.checkUsername(usernameValue)){
            register.recordError(username, "Username is taken.");
        }
    }

    @CommitAfter
    Object onSuccess() {
        user.setFirstName(firstnameValue);
        user.setLastName(lastnameValue);
        user.setEmail(emailValue);
        user.setUsername(usernameValue);
        user.setPassword(TrashHash.toHash(passwordValue));
        user.setRole("korisnik");
        user.setCreateTime(new Timestamp(new Date().getTime()));
        userDao.add(user);
        new File(userFolder + usernameValue).mkdirs(); //needs rework
        alert.success("Account created. Check your email for confimation link");
        
        return this;
    }

}
