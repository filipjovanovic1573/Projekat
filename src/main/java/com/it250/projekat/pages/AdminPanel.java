package com.it250.projekat.pages;

import com.it250.projekat.dao.SongDao;
import com.it250.projekat.dao.UserDao;
import com.it250.projekat.entities.Song;
import com.it250.projekat.entities.User;
import com.it250.projekat.other.Common;
import com.it250.projekat.services.ProtectedPage;
import java.io.File;
import java.util.ArrayList;
import javax.annotation.security.RolesAllowed;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

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
    @Persist
    private ArrayList<Song> songs;

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
    
    public Object onActionFromDownload(int id) {
        return Common.downloadSong(songDao, id);
    }
    
    @CommitAfter
    public Object onActionFromDelete(Song s){
        songDao.remove(s.getId(), Song.class);
        new File(s.getLink()).delete();
        return this;
    }
    
}
