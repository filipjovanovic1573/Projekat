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
import com.it250.projekat.services.ProtectedPage;
import java.io.File;
import javax.annotation.security.RolesAllowed;
import javax.sound.sampled.AudioFileFormat;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.apache.tapestry5.util.EnumSelectModel;

/**
 *
 * @author Filip
 */

@ProtectedPage
@RolesAllowed(value={"Korisnik", "Admin"})
public class Upload {

    //<editor-fold defaultstate="collapsed" desc="Properties and annotations">

    @Property @Validate("required")
    private UploadedFile file;
    
    @Property
    @Validate("maxlength=500")
    private String details;
    
    @InjectComponent
    private Form upload;

    @Property
    private String performer;

    @Property
    @Validate("required")
    private Genre genre;

    @Inject
    private AlertManager alertManager;
    
    @Inject
    private Messages messages;

    @Inject
    private SongDao dao;

    @SessionState
    private User user;

    private Song song;

    private String uploadFolder = Constants.USER_FOLDER + user.getUsername() + "\\";
    private String contextPath = "/" + user.getUsername() + "/";

    //</editor-fold>
    
    void onValidateFromUpload(){
        if(!file.getContentType().equals("audio/mp3")){
            upload.recordError("InvalidFormat");
            alertManager.alert(Duration.TRANSIENT, Severity.ERROR, messages.get("invalid_format"));
        }
        
        if(genre.equals(Genre.Genre)){
            upload.recordError("genre_error");
            alertManager.alert(Duration.TRANSIENT, Severity.ERROR, messages.get("invalid_genre"));
        }
    }
    
    @CommitAfter
    void onSuccessFromUpload() {
        song = new Song();
        song.setName(file.getFileName());
        song.setGenre(genre);
        song.setPerformer(performer);
        song.setDetails(details);
        song.setUserId(user);

        File tmp = new File(uploadFolder, file.getFileName());
        file.write(tmp);

        song.setLink(tmp.getPath());
        song.setContextPath(contextPath + file.getFileName());
        dao.add(song);

        System.out.println(file.getContentType());
        
        System.out.println(file.getContentType());
    }

    public SelectModel getModel() {
        return new EnumSelectModel(Genre.class, messages);
    }
}
