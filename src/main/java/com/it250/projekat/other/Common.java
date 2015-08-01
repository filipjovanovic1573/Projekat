/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.it250.projekat.other;

import com.it250.projekat.dao.SongDao;
import com.it250.projekat.entities.Song;
import com.it250.projekat.services.OutputStreamResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.io.IOUtils;
import org.apache.tapestry5.services.Response;

/**
 *
 * @author Workbench
 */
public class Common {
    public static Object downloadSong(SongDao songDao, int id){
        Song s = songDao.findById(id);
        
        final File file = new File(s.getLink());

        final OutputStreamResponse response = new OutputStreamResponse() {

            @Override
            public String getContentType() {
                return "audio/mp3";
            }

            @Override
            public void writeToStream(OutputStream out) throws IOException {
                try {
                    InputStream in = new FileInputStream(file);
                    IOUtils.copy(in, out);
                    in.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void prepareResponse(Response response) {
                response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
            }
        };

        return response;
    }
}
