/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.it250.projekat.dao;

import com.it250.projekat.entities.Comment;
import com.it250.projekat.entities.Song;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Workbench
 */
public class CommentDaoImpl extends GenericDaoImpl implements CommentDao{

    @Override
    public List<Comment> findComment(Song song) {
        return session.createCriteria(Comment.class).add(Restrictions.eq("songId", song)).list();
    }
    
}
