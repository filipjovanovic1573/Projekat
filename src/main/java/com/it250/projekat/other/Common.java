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
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
    public static void sendEmail(String recepient, String subject, String text){
        // Recipient's email ID needs to be mentioned.
      String to = recepient;

      // Sender's email ID needs to be mentioned
      String from = "MusicStore";

      // Assuming you are sending email from localhost
      String host = "localhost";

      // Get system properties
      Properties properties = System.getProperties();

      // Setup mail server
      properties.setProperty("mail.smtp.host", host);

      // Get the default Session object.
      Session session = Session.getDefaultInstance(properties);

      try{
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));

         // Set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO,
                                  new InternetAddress(to));

         // Set Subject: header field
         message.setSubject(subject);

         // Now set the actual message
         message.setText(text);

         // Send message
         Transport.send(message);
         System.out.println("Sent message successfully....");
      }catch (MessagingException mex) {
         mex.printStackTrace();
      }
    }
}
