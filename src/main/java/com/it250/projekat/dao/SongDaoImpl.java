/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.it250.projekat.dao;

import com.it250.projekat.entities.Song;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Filip
 */
public class SongDaoImpl extends GenericDaoImpl implements SongDao{

    @Override
    public List<Song> findByName(String name) {
        return session.createCriteria(Song.class).add(Restrictions.ilike("name", name + "%")).list();
    }

    @Override
    public int totalSongs() {
        return (Integer) session.createCriteria(Song.class).setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    public List<Song> loadSongsFrom(int from) {
        int page = (from - 1) * 10;
        return session.createCriteria(Song.class).setFirstResult(page).setMaxResults(10)
                .addOrder(Order.asc("id"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }
    
}
