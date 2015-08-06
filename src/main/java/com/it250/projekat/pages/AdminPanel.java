package com.it250.projekat.pages;

import com.it250.projekat.dao.SongDao;
import com.it250.projekat.dao.UserDao;
import com.it250.projekat.entities.Song;
import com.it250.projekat.entities.User;
import com.it250.projekat.other.Common;
import com.it250.projekat.other.Role;
import com.it250.projekat.services.ProtectedPage;
import java.util.ArrayList;
import javax.annotation.security.RolesAllowed;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;

/**
 *
 * @author Workbench
 */
@ProtectedPage
@RolesAllowed(value = {"Admin"})
public class AdminPanel {

    @Inject
    private UserDao userDao;

    @Inject
    private SongDao songDao;

    @Property
    @SessionState
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
        if (songs == null) {
            songs = new ArrayList<Song>();
        }
        songs = (ArrayList<Song>) songDao.findAll(Song.class);

        if (users == null) {
            users = new ArrayList<User>();
        }
        users = (ArrayList<User>) userDao.findAll(User.class);
    }

    public boolean isAdmin() {
        return user.getRole().equals(Role.Admin);
    }
    
    public Object onActionFromDownload(int id) {
        return Common.downloadSong(songDao, id);
    }
    
}
