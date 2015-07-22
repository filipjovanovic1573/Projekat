/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.it250.projekat.pages;

import com.it250.projekat.dao.SongDao;
import com.it250.projekat.entities.Song;
import com.it250.projekat.other.Genre;
import java.util.ArrayList;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
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

    @Inject
    AlertManager alert;

    @InjectComponent
    private Zone tablezone;

    @Inject
    private SongDao dao;

    @Property
    private Song song;

    @Property
    @Persist
    private ArrayList<Song> songs;

    @Inject
    private Messages messages;

    @Inject
    private ComponentResources resources;
    
    private Request request;
    //</editor-fold>

    void onActivate() {
        if (songs == null) {
            songs = new ArrayList<Song>();
        }
        //songs = (ArrayList<Song>) dao.findAll(Song.class);
    }

    Object onSuccessFromSearchform() {
        checkInput();
        if (songs == null || songs.size() < 1) {
            return this;
        } else {
            return this;
        }
    }

    private void checkInput() {
        if (genre.name().equals(genre.Genre.toString())) {
            songs = (ArrayList<Song>) dao.findSongsByName(searchValue);
        } else if (searchValue == null || searchValue.length() < 1) {
            songs = (ArrayList<Song>) dao.findSongsByGenre(genre.name());
        } else {
            songs = (ArrayList<Song>) dao.findSongs(searchValue, genre.name());
        }
    }

    public SelectModel getModel() {
        return new EnumSelectModel(Genre.class, messages);
    }
    /*
    public String getLink(){
        
    }
    */
    void pageReset() {
        songs.clear();
    }
}
