/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.it250.projekat.pages;

import com.it250.projekat.dao.SongDao;
import com.it250.projekat.entities.Song;
import com.it250.projekat.entities.User;
import com.it250.projekat.other.Common;
import com.it250.projekat.other.Genre;
import java.util.ArrayList;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.util.EnumSelectModel;

/**
 *
 * @author Workbench
 */
public class AdvancedSearch {

    //<editor-fold defaultstate="collapsed" desc="Properties and annotations">
    @Property
    private String searchValue;

    @Property
    @Persist
    @Validate("required")
    private Genre genre;

    @InjectComponent
    private Form searchform;

    @Inject
    private AlertManager alert;

    @Inject
    private SongDao dao;

    @Property
    private Song song;

    @Property @Persist
    private ArrayList<Song> songs;

    @Inject
    private Messages messages;
    
    @SessionState
    private User user;
    //</editor-fold>

    void onActivate() {
        if (songs == null) {
            songs = new ArrayList<Song>();
        }
    }

    Object onSuccessFromSearchform() {
        checkInput();
        return this;
    }

    private void checkInput() {
        if (genre.equals(genre.Genre) && searchValue != null) {
            songs = (ArrayList<Song>) dao.findSongsByName(searchValue);
        } else if (searchValue == null && !genre.equals(genre.Genre)) {
            songs = (ArrayList<Song>) dao.findSongsByGenre(genre);
        } else if (searchValue == null && genre.equals(genre.Genre)) {
            songs = (ArrayList<Song>) dao.findAll(Song.class);
        } else {
            songs = (ArrayList<Song>) dao.findSongs(searchValue, genre);
        }
    }

    public SelectModel getModel() {
        return new EnumSelectModel(Genre.class, messages);
    }
    
    public boolean isLoggedin() {
        return user.getId() != null;
    }
    
       public Object onActionFromDownload(int id) {
        return Common.downloadSong(dao, id);
    }
}
