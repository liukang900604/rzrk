/**
 * Project Name: rzrk Web
 * Confidential and Proprietary                                                                
 * Copyright (C) 2013 By                                                                                     
 * rzrk Company                 
 * All Rights Reserved                                                                                                                                                                                                                       
 */
package com.rzrk.common;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

public class GzipResponseStream extends ServletOutputStream {
	
	private final OutputStream outputStream;

    public GzipResponseStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void write(int b) throws IOException {
    	outputStream.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
    	outputStream.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
    	outputStream.write(b, off, len);
    }

}