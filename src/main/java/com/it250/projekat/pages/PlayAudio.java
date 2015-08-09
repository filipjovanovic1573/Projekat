/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.it250.projekat.pages;

import com.it250.projekat.dao.CommentDao;
import com.it250.projekat.entities.Comment;
import com.it250.projekat.entities.Song;
import com.it250.projekat.entities.User;
import java.util.ArrayList;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

/**
 *
 * @author Workbench
 */
public class PlayAudio {

    @Property
    @SessionState
    private User user;

    @Property
    @Persist
    private ArrayList<Comment> comments;

    @Inject
    private CommentDao dao;

    @Property
    private Comment comment;

    @InjectComponent
    private Zone commentzone;

    @Inject
    private AjaxResponseRenderer ajax;

    @Property
    @Validate("required")
    private String commentText;

    @Inject
    private Request request;

    @Property
    private Song song;
    
    private String path = "http://localhost:8080/musicstore/dir";
    
    void onActivate(Song s) {
        song = null;
        song = s;
    }

    void setupRender() {
        System.out.println(song.getName());
        if (comments == null) {
            comments = new ArrayList<Comment>();
        }
        comments = (ArrayList<Comment>) dao.findComment(song);
        ajax.addRender(commentzone);
    }

    @CommitAfter
    void onSuccessFromForm() {
        System.out.println(song.getName());
        Comment c = new Comment();
        c.setSongId(song);
        c.setUserId(user);
        c.setText(commentText);
        dao.add(c);
        if (request.isXHR()) {
            if (comments == null) {
                comments = new ArrayList<Comment>();
            }
            comments = (ArrayList<Comment>) dao.findComment(song);
            ajax.addRender(commentzone);
        }
    }

    public boolean isLoggedin() {
        return user.getId() != null;
    }

    public String getFile(){
        return path + song.getContextPath();
    }
    
}
