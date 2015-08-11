/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.it250.projekat.pages;

import com.it250.projekat.dao.UserDao;
import com.it250.projekat.entities.User;
import com.it250.projekat.other.Role;
import com.it250.projekat.other.TrashHash;
import com.it250.projekat.services.ProtectedPage;
import javax.annotation.security.RolesAllowed;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.corelib.components.Select;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.util.EnumSelectModel;

/**
 *
 * @author Filip
 */
@ProtectedPage
@RolesAllowed(value={"Admin"})
public class Edit {

    //<editor-fold defaultstate="collapsed" desc="Properties and annotations">
    @InjectComponent
    private TextField mail, username;

    @InjectComponent
    private BeanEditForm edit;

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

    @Property @Persist
    private User editUser;

    @Inject
    private UserDao dao;
    
    @Inject
    private AlertManager alert;
    
    @Inject
    private Messages messages;
    
    @Property
    private Role role;
    
    @InjectComponent
    private Select select;
    //</editor-fold>

    void onActivate(int id) {
        editUser = dao.getUserById(id);
        fillFields();
    }

    void onValidateFromEdit() {
        if (!dao.checkEmail(emailValue, editUser.getId())) {
            edit.recordError(mail, messages.get("edit_mail_error"));
        }

        if (!dao.checkUsername(usernameValue, editUser.getId())) {
            edit.recordError(username, messages.get("edit_username_error"));
        }
    }

    @CommitAfter
    Object onSuccess() {
        if (passwordValue != null) {
            editUser.setPassword(TrashHash.toHash(passwordValue));
        } else {
            editUser.setPassword(editUser.getPassword());
        }
        editUser.setUsername(usernameValue);
        editUser.setFirstName(firstnameValue);
        editUser.setLastName(lastnameValue);
        editUser.setEmail(emailValue);
        editUser.setRole(role);
        dao.merge(editUser);
        
        alert.alert(Duration.TRANSIENT, Severity.SUCCESS, messages.get("edit_success"));
        return AdminPanel.class;
    }

    Object onFailureFromEdit() {
        alert.alert(Duration.TRANSIENT, Severity.ERROR, messages.get("edit_error"));
        return null;
    }

    private void fillFields() {
        firstnameValue = editUser.getFirstName();
        lastnameValue = editUser.getLastName();
        emailValue = editUser.getEmail();
        usernameValue = editUser.getUsername();
        role = editUser.getRole();
    }
    
    
    public SelectModel getModel() {
        return new EnumSelectModel(Role.class, messages);
    }
}
