package com.it250.projekat.components;

import com.it250.projekat.entities.User;
import com.it250.projekat.pages.Index;
import java.util.Locale;
import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.services.PersistentLocale;

/**
 * Layout component for pages of application test-project.
 */
//
@Import(module = "bootstrap/collapse", stylesheet = "context:/css/auxiliary.css")
public class Layout {

    //<editor-fold defaultstate="collapsed" desc="Properties and annotations">
    @Inject
    private ComponentResources resources;

    @Inject
    private PersistentLocale persistentLocale;

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

    @Property
    @Inject
    @Symbol(SymbolConstants.APPLICATION_VERSION)
    private String appVersion;
//</editor-fold>

    void onActivate() {
        persistentLocale.set(Locale.ENGLISH);
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
        if (persistentLocale.get() != Locale.ENGLISH) {
            if (page.equals("AdvancedSearch")) {
                return "Napredna pretraga";
            } 
            else if(page.equals("Upload")){
                return "Postavi pesmu";
            }
            else {
                return page;
            }
        }

        if (page.equals("AdvancedSearch")) {
            return "Advanced search";
        } else {
            return page;
        }
    }

    public Object onActionFromLogout() {
        user = null;
        return Index.class;
    }

    public boolean isLoggedin() {
        if (user.getId() == null) {
            return false;
        } else {
            return true;
        }
    }
}
