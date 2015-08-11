/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.it250.projekat.pages;

import com.it250.projekat.dao.UserDao;
import com.it250.projekat.other.TrashHash;
import com.it250.projekat.entities.User;
import com.it250.projekat.other.Common;
import com.it250.projekat.other.Constants;
import com.it250.projekat.other.Role;
import com.it250.projekat.other.Style;
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
import org.apache.tapestry5.ioc.Messages;

/**
 *
 * @author Workbench
 */
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
    @Validate("regexp=^[A-Za-z ]+$")
    private String firstnameValue, lastnameValue;

    @Property
    @Validate("required, regexp=^[A-Za-z0-9-_.]+$")
    private String usernameValue;

    @Property
    @Validate("required, regexp=^[A-Za-z0-9-_<>]+$")
    private String passwordValue, confpasswordValue;

    @Property
    @Validate("required, email")
    private String emailValue;

    @Inject
    private UserDao userDao;
    
    @Inject
    private Messages messages;
    //</editor-fold>

    void onActivate() {
        
    }

    void onValidateFromRegister() {
        if (!passwordValue.equals(confpasswordValue)) {
            register.recordError(confirmPassword, messages.get("register_password_match"));
        }
        
        if(!userDao.checkEmail(emailValue)){
            register.recordError(mail, messages.get("register_mail_error"));
        }
        
        if(!userDao.checkUsername(usernameValue)){
            register.recordError(username, messages.get("register_username_error"));
        }
    }

    @CommitAfter
    Object onSuccess() {
        user.setFirstName(firstnameValue);
        user.setLastName(lastnameValue);
        user.setEmail(emailValue);
        user.setUsername(usernameValue);
        user.setPassword(TrashHash.toHash(passwordValue));
        user.setRole(Role.Korisnik);
        user.setStyle(Style.Dark);
        user.setCreateTime(new Timestamp(new Date().getTime()));
        userDao.add(user);
        new File(Constants.USER_FOLDER + usernameValue).mkdirs();
        alert.success(messages.get("reg_success"));
        return this;
    }

}
