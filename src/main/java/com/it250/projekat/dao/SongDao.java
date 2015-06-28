/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.it250.projekat.dao;

import com.it250.projekat.entities.Song;
import java.util.List;

/**
 *
 * @author Filip
 */
public interface SongDao extends GenericDao{
    public List<Song> findByName(String name);
    public int totalSongs();
    public List<Song> loadSongsFrom(int from);
}
