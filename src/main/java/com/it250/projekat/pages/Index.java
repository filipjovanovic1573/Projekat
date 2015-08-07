package com.it250.projekat.pages;

import com.it250.projekat.dao.SongDao;
import com.it250.projekat.entities.Song;
import com.it250.projekat.entities.User;
import com.it250.projekat.other.Common;
import java.util.ArrayList;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Index {
    //<editor-fold defaultstate="collapsed" desc="Properties and annotations">
    @Inject
    private SongDao dao;

    @Property
    private ArrayList<Song> songs;

    @Property
    private Song song;
    
    @SessionState
    private User user;
    //</editor-fold>
    
    void onActivate() {
        if (songs == null) {
            songs = new ArrayList<Song>();
        }
        songs = (ArrayList<Song>) dao.findLatest();
    }
    public Object onActionFromDownload(int id) {
        return Common.downloadSong(dao, id);
    }
    public boolean isLoggedin() {
        return user.getId() != null;
    }
}