/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.it250.projekat.pages;

import com.it250.projekat.dao.SongDao;
import com.it250.projekat.entities.Song;
import com.it250.projekat.entities.User;
import com.it250.projekat.other.Constants;
import com.it250.projekat.other.Genre;
import java.io.File;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.apache.tapestry5.util.EnumSelectModel;

/**
 *
 * @author Filip
 */
public class Upload {

    //<editor-fold defaultstate="collapsed" desc="Properties and annotations">

    @Property
    private UploadedFile file;

    @Property
    @Validate("maxlength=500")
    private String details;

    @Property
    private String performer;

    @Property
    @Validate("required")
    private Genre genre;

    @Inject
    private Messages messages;

    @Inject
    private SongDao dao;

    @SessionState
    private User user;

    private Song song;

    private String uploadFolder = Constants.USER_FOLDER + user.getUsername() + "\\";

    //</editor-fold>
    @CommitAfter
    void onSuccessFromUpload() {
        song = new Song();
        song.setName(file.getFileName());
        song.setGenre(genre);
        song.setPerformer(performer);
        song.setDetails(details);

        File tmp = new File(uploadFolder, file.getFileName());
        file.write(tmp);

        song.setLink(tmp.getPath());
        dao.add(song);

        System.out.println(file.getContentType());
    }

    public SelectModel getModel() {
        return new EnumSelectModel(Genre.class, messages);
    }
}
