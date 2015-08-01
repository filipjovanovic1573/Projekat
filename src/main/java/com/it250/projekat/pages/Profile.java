/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.it250.projekat.pages;

import com.it250.projekat.dao.SongDao;
import com.it250.projekat.dao.UserDao;
import com.it250.projekat.entities.Song;
import com.it250.projekat.entities.User;
import com.it250.projekat.other.TrashHash;
import java.util.ArrayList;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Profile {

    //<editor-fold defaultstate="collapsed" desc="Properties, annotations and variables">
    @InjectComponent
    private BeanEditForm edit;

    @InjectComponent
    private TextField mail, username;

    @Property
    @SessionState
    private User user;

    @Property
    @Validate("regexp=^[A-Za-z ]+$")
    private String firstnameValue, lastnameValue;

    @Property
    @Validate("required, regexp=^[A-Za-z0-9-_]+$")
    private String usernameValue;

    @Property
    @Validate("regexp=^[A-Za-z0-9-_<>]+$")
    private String passwordValue;

    @Property
    @Validate("required, email")
    private String emailValue;

    @Inject
    private UserDao userDao;

    @Inject
    private SongDao songDao;

    @Property
    private boolean userExists;

    @Inject
    private Messages messages;

    @Property
    private ArrayList<Song> uploaded, downloaded;

    @Property
    private Song song;
    //</editor-fold>

    void onActivate() {
        fillFields();

        uploaded = new ArrayList<Song>();
        uploaded = (ArrayList<Song>) songDao.findByUserId(user);
    }

    void onValidateFromEdit() {
        if (!userDao.checkEmail(user.getEmail(), user.getId())) {
            edit.recordError(mail, messages.get("profile_mail_error"));
        }

        if (!userDao.checkUsername(user.getUsername(), user.getId())) {
            edit.recordError(username, messages.get("profile_username_error"));
        }
    }

    Object onFailureFromEdit() {
        return null;
    }

    @CommitAfter
    Object onSuccess() {
        if (passwordValue.equals("")) {
            user.setPassword(TrashHash.toHash(passwordValue));
        } else {
            user.setPassword(user.getPassword());
        }
        user.setUsername(usernameValue);
        userDao.merge(user);
        return null;
    }

    private void fillFields() {
        firstnameValue = user.getFirstName();
        lastnameValue = user.getLastName();
        emailValue = user.getEmail();
        usernameValue = user.getUsername();
    }
}
