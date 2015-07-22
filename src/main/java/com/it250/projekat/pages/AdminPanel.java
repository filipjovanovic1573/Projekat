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
import com.it250.projekat.other.Role;
import java.util.ArrayList;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;

/**
 *
 * @author Workbench
 */
public class AdminPanel {

    @Inject
    private UserDao userDao;

    @Inject
    private SongDao songDao;

    @Property @SessionState
    private User user;
    
    @Property
    private User editUser;

    @Property
    private Song song;
    
    @Property
    private ArrayList<User> users;
    
    @Property
    private ArrayList<Song> songs;

    @Property
    private boolean userExists;

    void onActivate() {
        if(songs == null){
            songs = new ArrayList<Song>();
        }
        songs = (ArrayList<Song>) songDao.findAll(Song.class);
        
        if (users == null) {
            users = new ArrayList<User>();
        }
        users = (ArrayList<User>) userDao.findAll(User.class);
    }

    public JSONObject getOptions() {

        // The available options are documented at http://www.datatables.net/ref
        JSONObject options = new JSONObject();
        options.put("bJQueryUI", "true");

        return options;
    }
    
    public boolean isAdmin() {
        return user.getRole().equals(Role.Admin);
    }
}
