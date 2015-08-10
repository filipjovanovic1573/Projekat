/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.it250.projekat.services;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.tapestry5.services.Response;

/**
 *
 * @author Workbench
 */
public interface OutputStreamResponse {
    String getContentType();

    /**
     * Implements a callback to directly write to the output stream.
     * The stream will be closed after this method returns.
     * The provided stream is wrapped in a {@link BufferedOutputStream} for efficiency.
     * @param out
     * @throws java.io.IOException
     */
    public void writeToStream(OutputStream out) throws IOException;

    void prepareResponse(Response response);
}
