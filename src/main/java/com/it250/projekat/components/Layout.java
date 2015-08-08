package com.it250.projekat.components;

import com.it250.projekat.entities.User;
import com.it250.projekat.other.Role;
import com.it250.projekat.other.Style;
import com.it250.projekat.pages.Index;
import java.util.Locale;
import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.services.PersistentLocale;

/**
 * Layout component for pages of application test-project.
 */
//
@Import(module = "bootstrap/collapse")
public class Layout {

    //<editor-fold defaultstate="collapsed" desc="Properties and annotations">
    @Inject
    private ComponentResources resources;

    @Inject
    private PersistentLocale persistentLocale;

    @Inject
    @Path("context:css/auxiliary.css")
    private Asset darkTheme;

    @Inject
    @Path("context:css/auxiliaryw.css")
    private Asset lightTheme;

    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String title;

    @Property
    private String pageName;

    @Property
    @SessionState
    User user;

    @Property
    private boolean userExists;

    @Inject
    private Messages message;

    @Property
    @Inject
    @Symbol(SymbolConstants.APPLICATION_VERSION)
    private String appVersion;
//</editor-fold>

    void onActivate() {
    }

    public String getClassForPageName() {
        return resources.getPageName().equalsIgnoreCase(pageName)
                ? "active"
                : null;
    }

    public String[] getPageNames() {
        return new String[]{"AdvancedSearch", "Upload"};
    }

    public String renamePage(String page) {
        if (page.equals("AdvancedSearch")) {
            return message.get("advanced_search");
        } else {
            return message.get("upload");
        }
    }

    public Object onActionFromLogout() {
        user = null;
        return Index.class;
    }

    public boolean isLoggedin() {
        return user.getId() != null;
    }

    public boolean isAdmin() {
        if (isLoggedin()) {
            return user.getRole().equals(Role.Admin);
        }
        return false;
    }

    public void onActionFromSr() {
        persistentLocale.set(new Locale("sr_RS"));
    }

    public void onActionFromEn() {
        persistentLocale.set(new Locale("en"));
    }

    public Asset getStyle() {
        if (isLoggedin() != false) {
            if (user.getStyle().equals(Style.Dark)) {
                return darkTheme;
            } else if (user.getStyle().equals(Style.Light)) {
                return lightTheme;
            } else {
                return darkTheme;
            }
        } else {
            return darkTheme;
        }
    }
}
