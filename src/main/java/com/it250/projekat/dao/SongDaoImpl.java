/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.it250.projekat.dao;

import com.it250.projekat.entities.Song;
import com.it250.projekat.other.Genre;
import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Filip
 */
public class SongDaoImpl extends GenericDaoImpl implements SongDao{

    @Override
    public List<Song> findSongs(String name, Genre genre) {
        return session.createCriteria(Song.class).add(Restrictions.eq("name", name)).add(Restrictions.eq("genre", genre)).list();
    }

    @Override
    public List<Song> findSongsByGenre(Genre genre) {
        return session.createCriteria(Song.class).add(Restrictions.eq("genre", genre)).list();
    }

    @Override
    public List<Song> findSongsByName(String name) {
        return session.createCriteria(Song.class).add(Restrictions.ilike("name", name)).list();
    }

    @Override
    public List<Song> findLatest() {
        return session.createCriteria(Song.class).addOrder(Order.desc("id")).setMaxResults(20).list();
    }
    
}
