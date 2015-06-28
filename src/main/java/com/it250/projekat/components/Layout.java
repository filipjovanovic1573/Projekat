package com.it250.projekat.components;

import com.it250.projekat.entities.User;
import com.it250.projekat.pages.Index;
import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.SymbolConstants;

/**
 * Layout component for pages of application test-project.
 */
//
@Import(module = "bootstrap/collapse", stylesheet = "context:/css/auxiliary.css")
public class Layout {

    //<editor-fold defaultstate="collapsed" desc="Properties and annotations">

    @Inject
    private ComponentResources resources;
    
    /**
     * The page title, for the <title> element and the <h1> element.
     */
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String title;

    @Property
    private String pageName, searchValue;

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

    public String getClassForPageName() {
        return resources.getPageName().equalsIgnoreCase(pageName)
                ? "active"
                : null;
    }

    public String[] getPageNames() {
        return new String[]{"Index", "About", "Contact", "AdvancedSearch"};
    }

    public String renamePage(String page){
        if(page.equals("AdvancedSearch"))
            return "Advanced Search";
        else
            return page;
    }
    
    public Object onActionFromLogout() {
        user = null;
        return Index.class;
    }
    
    public boolean isLoggedin(){
        if(user.getId() == null){
            return false;
        }
        else {
            return true;
        }  
    }
}
