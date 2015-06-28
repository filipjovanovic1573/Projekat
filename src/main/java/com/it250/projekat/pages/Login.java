package com.it250.projekat.pages;

import com.it250.projekat.dao.UserDao;
import com.it250.projekat.entities.User;
import com.it250.projekat.other.TrashHash;
import java.util.ArrayList;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Login {

    //<editor-fold defaultstate="collapsed" desc="Properties and annotations">
    @Inject
    private AlertManager alertManager;

    @Inject
    private UserDao userDao;
    
    @InjectComponent
    private BeanEditForm login;

    @InjectComponent
    private TextField mail;

    @InjectComponent
    private PasswordField password;

    @Property @Validate("required, email") @Persist
    private String emailValue;

    @Property @Validate("required") @Persist
    private String passwordValue;

    @Property
    private ArrayList<User> users;
    
    @SessionState @Property
    private User user;
    
    private boolean userExists;
    //</editor-fold>

    
    void onActivate(){
        if(users == null){
            users = new ArrayList<User>();
        }
        
        users = (ArrayList<User>)userDao.findAll(User.class);
    }
    
    void onValidateFromLogin(){
        if(emailValue.length() == 0) {
            login.recordError(mail, "Email address cant be empty");
        }
        
        if(passwordValue.length() == 0){
            login.recordError(password, "Password cant be empty");
        }
        
    }
    
    Object onSuccess() {
        user = userDao.checkUser(emailValue, TrashHash.toHash(passwordValue));
        if(!userExists){
            alertManager.error("User not found");
            return this;
        }
        
        return Index.class;
    }
}
