package com.it250.projekat.pages;

import com.it250.projekat.dao.UserDao;
import com.it250.projekat.entities.User;
import java.util.ArrayList;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;

public class Index {
    
    @Inject
    private UserDao dao;
    
    @Property
    private ArrayList<User> users;
    
    @Property
    private User user;
    
    void setupRender(){
        if(users == null){
            users = new ArrayList<User>();
        }
        users = (ArrayList<User>) dao.findAll(User.class);
    }
    
    public JSONObject getOptions() {
        
        // The available options are documented at http://www.datatables.net/ref
        JSONObject options = new JSONObject();
        options.put("bJQueryUI", "true");
        
        return options;
    }

}
