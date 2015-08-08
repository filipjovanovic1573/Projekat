/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.it250.projekat.pages;

import com.it250.projekat.entities.Song;
import com.it250.projekat.entities.User;
import java.util.ArrayList;
import javax.xml.stream.events.Comment;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;

/**
 *
 * @author Workbench
 */
public class PlayAudio {
    @Property
    @SessionState
    private User user;
    
    @Property
    private ArrayList<Comment> comments;
    
    @Property
    private Song song;
    
    void onActivate(Song s){
        song = s;
        
        if(comments == null){
            comments = new ArrayList<Comment>();
        }
    }
}
