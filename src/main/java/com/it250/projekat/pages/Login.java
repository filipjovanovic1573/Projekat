package com.it250.projekat.pages;

import com.it250.projekat.dao.UserDao;
import com.it250.projekat.entities.User;
import com.it250.projekat.other.Constants;
import com.it250.projekat.other.TrashHash;
import java.io.File;
import java.util.ArrayList;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
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

    @Property
    @Validate("required, email")
    @Persist
    private String emailValue;

    @Property
    @Validate("required")
    @Persist
    private String passwordValue;

    @SessionState
    @Property
    private User user;

    @Inject
    private Messages messages;

    private File file;
    private boolean userExists;
    //</editor-fold>

    void onActivate() {
        file = new File(Constants.USER_FOLDER + user.getUsername() + "\\");
    }

    void onValidateFromLogin() {
        if (emailValue.length() == 0) {
            login.recordError(mail, messages.get("login_mail_error"));
        }

        if (passwordValue.length() == 0) {
            login.recordError(password, messages.get("login_password_error"));
        }

    }

    Object onSuccess() {
        user = userDao.checkUser(emailValue, TrashHash.toHash(passwordValue));
        if (!userExists) {
            alertManager.alert(Duration.TRANSIENT, Severity.ERROR, messages.get("login_user_not_found"));
            return this;
        } else {
            if (!file.exists()) {
                file.mkdirs();
            }
        }

        return Index.class;
    }
}
